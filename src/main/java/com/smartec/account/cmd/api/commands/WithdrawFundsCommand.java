package com.smartec.account.cmd.api.commands;

import com.smartec.cqrs.core.commands.BaseCommand;
import lombok.Data;

@Data
public class WithdrawFundsCommand extends BaseCommand {
    private double amount;
}
