package cn.eunoiay.cli.test;

import cn.eunoiay.cli.core.CommandLine;

/**
 * @author Eunoiay
 */
public class CommandTest {

    public static void main(String[] args) {
        CommandLine.run("hello", "-l", "en", "World");
    }
    
}
