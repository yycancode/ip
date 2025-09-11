package yy.parser;

/**
 * Parses raw user input strings into command types.
 * <p>
 * Responsible for identifying which command the user intended,
 * such as todo, deadline, event, list, mark, unmark, delete, or bye.
 */

public class Parser {
    /**
     * Represents the different types of commands supported by the application.
     */
    public enum Command {
        BYE, LIST, MARK, UNMARK, TODO, DEADLINE, EVENT, DELETE, FIND, UNKNOWN }

    /**
     * Parses a raw input string and returns the corresponding command type.
     * <p>
     * Matches against known command keywords and returns UNKNOWN if no match is found.
     *
     * @param input the raw user input string
     * @return the identified Command type
     */
    public Command parseCommand(String input) {
        String s = input.toLowerCase();
        if (s.equals("bye")) {
            return Command.BYE;
        } else if (s.equals("list")) {
            return Command.LIST;
        } else if (s.startsWith("mark ")) {
            return Command.MARK;
        } else if (s.startsWith("unmark ")) {
            return Command.UNMARK;
        } else if (s.equals("todo") || s.startsWith("todo ")) {
            return Command.TODO;
        } else if (s.equals("deadline") || s.startsWith("deadline ")) {
            return Command.DEADLINE;
        } else if (s.equals("event") || s.startsWith("event ")) {
            return Command.EVENT;
        } else if (s.equals("delete") || s.startsWith("delete ")) {
            return Command.DELETE;
        } else if (s.equals("find") || s.startsWith("find ")) {
            return Command.FIND;
        } else {
            return Command.UNKNOWN;
        }
    }
}
