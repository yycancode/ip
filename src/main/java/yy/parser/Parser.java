package yy.parser;

public class Parser {
    public enum Command { BYE, LIST, MARK, UNMARK, TODO, DEADLINE, EVENT, DELETE, UNKNOWN }

    public Command parseCommand(String input) {
        String s = input.toLowerCase();
        if (s.equals("bye")) return Command.BYE;
        if (s.equals("list")) return Command.LIST;
        if (s.startsWith("mark ")) return Command.MARK;
        if (s.startsWith("unmark ")) return Command.UNMARK;
        if (s.equals("todo") || s.startsWith("todo ")) return Command.TODO;
        if (s.equals("deadline") || s.startsWith("deadline ")) return Command.DEADLINE;
        if (s.equals("event") || s.startsWith("event ")) return Command.EVENT;
        if (s.equals("delete") || s.startsWith("delete ")) return Command.DELETE;
        return Command.UNKNOWN;
    }
}