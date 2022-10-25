package com.smartec.account.cmd.domaine;

import com.smartec.account.cmd.api.commands.OpenAccountCommand;
import com.smartec.account.common.events.AccountOpenedEvent;
import com.smartec.account.common.events.FundsDepositEvent;
import com.smartec.account.common.events.FundsWithdrawnEvent;
import com.smartec.cqrs.core.domain.AggregateRoot;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
public class AccountAggregate extends AggregateRoot {
    private boolean active;
    private double balance;

    public AccountAggregate(OpenAccountCommand command) {
        raiseEvent(AccountOpenedEvent.builder()
                .id(command.getId())
                .accountHolder(command.getAccountHolder())
                .createdDate(new Date())
                .accountType(command.getAccountType())
                .openingBalance(command.getOpeningBalance())
                .build());
    }

    //this will be used by the reflection created in the aggregateRoot
    public void apply(AccountOpenedEvent event){
        this.id = event.getId();
        this.active = true;
        this.balance = event.getOpeningBalance();
    }

    public void apply(FundsDepositEvent event){
        this.id = event.getId();
        this.active =  true;
        this.balance += event.getAmount();
    }

    public void apply(FundsWithdrawnEvent event){
        this.id = event.getId();
        this.balance -= event.getAmount();
    }

    public void depositFunds(double amount) {
        if (!this.active) {
            throw  new IllegalStateException("Funds cannot be deposited in a closed account");
        }
        raiseNonEligibleAmountException(amount);

        //this will raise an event that will be applied by the aggregate to add the amount
        raiseEvent(FundsDepositEvent.builder()
                .id(this.id)
                .amount(amount)
                .build());
    }

    public void withdrawFunds(double amount) {
        if (!this.active) {
            throw  new IllegalStateException("Funds cannot be withdrawn from a closed account");
        }
        raiseNonEligibleAmountException(amount);
        raiseEvent(FundsWithdrawnEvent.builder()
                .id(this.id)
                .amount(amount)
                .build());
    }


    private void raiseNonEligibleAmountException(double amount) {
        if (amount <= 0) {
            throw new IllegalStateException("the deposit amount must be greater than 0")
        }
    }
}
