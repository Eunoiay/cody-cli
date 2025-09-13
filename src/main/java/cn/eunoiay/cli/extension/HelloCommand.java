package cn.eunoiay.cli.extension;

import cn.eunoiay.cli.core.command.Command;
import cn.eunoiay.cli.core.option.Option;
import cn.eunoiay.cli.core.parameter.Parameter;

/**
 * @author Eunoiay
 */
@Command(name = "hello")
public class HelloCommand implements Runnable {

    @Option(names = { "-l", "--language" }, defaultValue = "zh")
    private String language;
    
    @Parameter(index = 0)
    private String name;
    
    @Override
    public void run() {
        String greeting = getGreeting(language);
        System.out.print(greeting + " " + name + "!");
    }
    
    private String getGreeting(String lang) {
        return switch (lang.toLowerCase()) {
            case "en", "english" -> "Hello";
            default -> "你好";
        };
    }
    
}
