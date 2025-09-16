# YY's chatbot User Guide

## Introduction  
YY is a desktop task manager chatbot for managing todos, deadlines, and events. It is optimized for use via the Command Line Interface (CLI) while still offering a JavaFX Graphical User Interface (GUI). If you type fast, YY lets you manage tasks more efficiently than traditional GUI apps.

---

## Quick start  

1. Ensure you have **Java 17** installed.  
2. Download the latest release JAR file (or compile from source).  
3. Copy the JAR file to the folder you want to use as the home folder.  
4. Open a terminal, `cd` into that folder, and run:  
   ```bash
   java -jar yy.jar
   ```
5. A CLI (or GUI, if launched) will appear.  
6. Type commands into the box and press Enter to execute them.  

Some example commands you can try:  

- `list` : Lists all tasks.  
- `todo [task]` : Adds a Todo task.  
- `deadline [task] /by [yyyy-mm-dd]` : Adds a Deadline task.  
- `event [task] /from [yyyy-mm-dd] [hhmm] /to [yyyy-mm-dd] [hhmm]` : Adds an Event task.  
- `mark 2` : Marks the 2nd task as done.  
- `undo` : Reverts the last change.  
- `bye` : Exits the app.  

---

## Features  

ℹ️ **Notes about command format**  
- Words in `UPPER_CASE` are parameters supplied by you.  
- Square brackets `[ ]` mean optional.  
- Index numbers are 1-based (as shown in `list`).  
- Extra parameters for commands that don’t need them are ignored.  

### Viewing help: `help`  
Shows usage instructions.  
**Format:** `help`  

### Adding a todo: `todo`  
**Format:** `todo DESCRIPTION`  
**Example:** `todo read book`  

### Adding a deadline: `deadline`  
**Format:** `deadline DESCRIPTION /by BY_DATETIME`  
**Example:** `deadline finish report /by 2025-10-01`  

### Adding an event: `event`  
**Format:** `event DESCRIPTION /from START /to END`  
**Example:** `event hackathon /from 2025-10-10 0900 /to 2025-10-10 2100`  

### Listing tasks: `list`  
**Format:** `list`  
Lists all tasks in storage.  

### Marking / Unmarking tasks: `mark`, `unmark`  
**Format:** `mark INDEX` / `unmark INDEX`  
**Example:** `mark 2`  

### Deleting tasks: `delete`  
**Format:** `delete INDEX`  
**Example:** `delete 3`  

### Finding tasks: `find`  
**Format:** `find KEYWORD [MORE_KEYWORDS]`  
Case-insensitive search over descriptions.  
**Example:** `find book report`  

### Undo last change: `undo`  
**Format:** `undo`  
Reverts the previous mutating command.  

### Exiting: `bye`  
**Format:** `bye`  
Exits the program.  

### Saving data  
YY saves data automatically after each command that modifies tasks.  

### Editing the data file  
Tasks are stored in a plain-text file, one task per line with pipe (`|`) separators.  
Advanced users can edit it directly, but malformed lines will be skipped at next load.  

---

## FAQ  

**Q: How do I move my data to another computer?**  
A: Copy the data file from your old computer into the new computer’s home folder.  

---

## Known issues  

- On some systems, missing `dos2unix` may cause line-ending differences in text-ui-tests. A Perl fallback is included.  
- If your main class name is wrong (`Duke` vs `YY`), text-ui-tests will produce an empty ACTUAL.TXT. Fix the script to run `YY`.  

---

## Command summary  

| Action  | Format | Example |
|---------|--------|---------|
| Help    | `help` | `help` |
| Todo    | `todo DESCRIPTION` | `todo read book` |
| Deadline| `deadline DESCRIPTION /by BY_DATETIME` | `deadline finish report /by 2025-10-01` |
| Event   | `event DESCRIPTION /from START /to END` | `event hackathon /from 2025-10-10 0900 /to 2025-10-10 2100` |
| List    | `list` | `list` |
| Mark    | `mark INDEX` | `mark 2` |
| Unmark  | `unmark INDEX` | `unmark 2` |
| Delete  | `delete INDEX` | `delete 3` |
| Find    | `find KEYWORD [MORE_KEYWORDS]` | `find book report` |
| Undo    | `undo` | `undo` |
| Bye     | `bye` | `bye` |

---
