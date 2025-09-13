package cn.eunoiay.cli.core.command;

import cn.eunoiay.cli.core.option.Option;
import cn.eunoiay.cli.core.option.OptionSpec;
import cn.eunoiay.cli.core.parameter.Parameter;
import cn.eunoiay.cli.core.parameter.ParameterSpec;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.lang.reflect.Field;
import java.util.*;

/**
 * @author Eunoiay
 */
@Accessors(fluent = true)
public class CommandSpec {

    @Getter
    private final String name;

    @Getter
    private final List<String> aliases;

    private final Class<?> commandClass;

    private final Map<String, OptionSpec> optionSpecs = new HashMap<>();

    private final Map<Integer, ParameterSpec> parameterSpecs = new HashMap<>();
    
    public CommandSpec(Class<?> clazz) {
        Command command = clazz.getAnnotation(Command.class);
        if (command == null) {
            throw new IllegalArgumentException("Class must be annotated with @Command: " + clazz.getName());
        }
        this.name = command.name().isEmpty() ? clazz.getSimpleName().toLowerCase() : command.name();
        this.aliases = Arrays.asList(command.aliases());
        this.commandClass = clazz;
        init();
    }

    private void init() {
        Field[] fields = commandClass.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            Option option = field.getAnnotation(Option.class);
            if (option != null) {
                for (String name : option.names()) {
                    optionSpecs.put(name, new OptionSpec(field));
                }
            }
            Parameter parameter = field.getAnnotation(Parameter.class);
            if (parameter != null) {
                parameterSpecs.put(parameter.index(), new ParameterSpec(field));
            }
        }
    }
    
    public int execute(String[] args) throws Exception {
        Object instance = parseArguments(args);
        // 校验：设置默认值 TODO 其他校验
        for (OptionSpec optionSpec : optionSpecs.values()) {
            optionSpec.validate(instance);
        }
        if (instance instanceof Runnable) {
            ((Runnable)instance).run();
        } else {
            throw new IllegalStateException("Command class must implement Runnable: " + commandClass.getName());
        }
        return 0;
    }
    
    private Object parseArguments(String[] args) throws Exception {
        Object instance = commandClass.getDeclaredConstructor().newInstance();
        for (int i = 0, j = 0; i < args.length; i++) {
            String arg = args[i];
            if (arg.startsWith("-")) {
                OptionSpec optionSpec = optionSpecs.get(arg);
                if (optionSpec != null) {
                    if (optionSpec.isFlag()) {
                        optionSpec.setValue(instance, null);
                        continue;
                    }
                    if (i + 1 < args.length && !args[i + 1].startsWith("-")) {
                        optionSpec.setValue(instance, args[++i]);
                        continue;
                    }
                }
                continue;
            }
            ParameterSpec parameterSpec = parameterSpecs.get(j++);
            parameterSpec.setValue(instance, arg);
        }
        return instance;
    }
}
