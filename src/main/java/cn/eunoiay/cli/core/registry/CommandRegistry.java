package cn.eunoiay.cli.core.registry;

import cn.eunoiay.cli.core.command.CommandSpec;
import cn.eunoiay.cli.extension.HelloCommand;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 命令注册表
 * @author Eunoiay
 */
public class CommandRegistry {
    
    private static final Map<String, CommandSpec> commands = new HashMap<>();
    
    private CommandRegistry() {
    }
    
    static  {
        init();
    }
    
    public static void register(Class<?> clazz) {
        CommandSpec spec = new CommandSpec(clazz);
        commands.put(spec.name(), spec);
        for (String alias : spec.aliases()) {
            commands.put(alias, spec);
        }
    }
    
    public static CommandSpec get(String name) {
        CommandSpec commandSpec = commands.get(name);
        return Optional.ofNullable(commandSpec).orElseThrow(() -> new RuntimeException("Unknown command: " + name));
    }
    
    private static void init() {
        // TODO: 读取注册文件实现
        register(HelloCommand.class);
    }
    
}
