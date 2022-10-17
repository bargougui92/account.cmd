package com.smartec.account.cmd.infrastructure;

import com.smartec.cqrs.core.commands.BaseCommand;
import com.smartec.cqrs.core.commands.CommandHanlderMethod;
import com.smartec.cqrs.core.infrastructure.CommandDispatcher;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class AccountCommandDispatcher implements CommandDispatcher {

    //represents a collection of registred command handler methods
    private final Map<Class<? extends BaseCommand>, List<CommandHanlderMethod>> routes = new HashMap<>();

    @Override
    public <T extends BaseCommand> void registerHandler(Class<T> type, CommandHanlderMethod<T> handler) {
        List<CommandHanlderMethod> handlers = routes.computeIfAbsent(type, c -> new LinkedList<>());
         handlers.add(handler);
    }


    //dispatches a given command to a registred command handling method
    @Override
    public void send(BaseCommand command) {
        var handlers = routes.get(command.getClass());
        if (handlers.isEmpty())
          {
              throw new RuntimeException("No command handler was registered!");
          }
        if (handlers.size()> 1)
          {
              throw new RuntimeException("Cannot send command to more than one handler");
          }
        handlers.get(0).handle(command);
    }
}
