package com.smartec.account.cmd.api.commands;

import com.smartec.cqrs.core.commands.BaseCommand;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class DepositFundsCommand extends BaseCommand {
    private double amount;
}
