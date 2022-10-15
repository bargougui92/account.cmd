package com.smartec.account.cmd.api.commands;

import com.smartec.account.common.dto.AccountType;
import com.smartec.cqrs.core.commands.BaseCommand;
import lombok.Data;

@Data
public class OpenAccountCommand extends BaseCommand {
    private String accountHolder;
    private AccountType accountType;
    private double openingBalance;

}
