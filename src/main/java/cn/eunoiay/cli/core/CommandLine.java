package cn.eunoiay.cli.core;

import cn.eunoiay.cli.core.command.CommandSpec;
import cn.eunoiay.cli.core.registry.CommandRegistry;

import java.util.Arrays;

/**
 * @author Eunoiay
 */
public class CommandLine {

    private static int execute(String... args) {
        if (args.length == 0) {
            System.out.println("Usage: <command> [options]");
            return 1;
        }
        try {
            // TODO: 处理全局选项
            CommandSpec command = CommandRegistry.get(args[0]);
            return command.execute(Arrays.copyOfRange(args, 1, args.length));
        } catch (Exception e) {
            // TODO: 异常处理
            System.err.println(e.getMessage());
            return 1;
        }
    }
    
    public static void run(String... args) {
        int exitCode = execute(args);
        System.exit(exitCode);
    }
    
}
