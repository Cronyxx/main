package seedu.address.logic.commands;

import static seedu.address.commons.core.Messages.MESSAGE_NOT_IN_SERVE_MODE;
import static seedu.address.commons.core.Messages.MESSAGE_UNUSED_ARGUMENT;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Exits the serve mode.
 */
public class DoneCommand extends ReversibleCommand {
    public static final String COMMAND_WORD = "done";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Returns to List Mode. \n"
            + "Example: " + COMMAND_WORD;
    public static final String MESSAGE_SUCCESS = "Exited from Serve Mode. ";

    private String unusedArguments = null;

    public DoneCommand() {}

    public DoneCommand(String unusedArguments) {
        if (!unusedArguments.equals("")) {
            this.unusedArguments = unusedArguments;
        }
    }

    /**
     * Executes the command and returns the result message.
     *
     * @param model {@code Model} which the command should operate on.
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {

        if (!model.isServeMode()) {
            throw new CommandException(MESSAGE_NOT_IN_SERVE_MODE);
        }

        undoCommand = new ServeCommand(model.getServingBorrower().getBorrowerId());
        redoCommand = this;

        if (unusedArguments != null) {
            commandResult = new CommandResult(String.format(MESSAGE_SUCCESS
                    + MESSAGE_UNUSED_ARGUMENT, unusedArguments, COMMAND_WORD),
                    false, false, false, true);
        } else {
            commandResult = new CommandResult(MESSAGE_SUCCESS, false, false, false, true);
        }

        model.exitsServeMode();
        return commandResult;
    }

    @Override
    public boolean equals(Object other) {
        return (other == this || other instanceof DoneCommand);
    }
}