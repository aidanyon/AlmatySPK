package timmy.services;

import timmy.command.Command;
import timmy.command.CommandFactory;
import timmy.dao.DaoFactory;
import timmy.dao.impl.ButtonDao;
import timmy.entity.standart.Button;
import timmy.exceptions.CommandNotFoundException;
import timmy.utils.Const;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

public class CommandService {

    private DaoFactory factory = DaoFactory.getInstance();
    private ButtonDao  buttonDao;

    public Command getCommand(Update update) throws CommandNotFoundException {
        buttonDao = factory.getButtonDao();
        Message updateMessage = update.getMessage();
        String inputtedText;
        if (update.hasCallbackQuery()) {
            inputtedText = update.getCallbackQuery().getData().split(Const.SPLIT)[0];
            updateMessage = update.getCallbackQuery().getMessage();
            try {
                if (inputtedText != null && inputtedText.substring(0,6).equals(Const.ID_MARK)) {
                    try {
                        return getCommandById(Integer.parseInt(inputtedText.split(Const.SPLIT)[0].replaceAll(Const.ID_MARK, "")));
                    } catch (Exception e) {
                        inputtedText = updateMessage.getText();
                    }
                }
            } catch (Exception e) {}
        } else {
            try {
                inputtedText = updateMessage.getText();
            } catch (Exception e) {
                throw new CommandNotFoundException(new Exception("No data is available"));
            }
        }
        Button button = buttonDao.getButton(inputtedText);
        return getCommand(button);
    }

    private Command getCommand(Button button) throws CommandNotFoundException {
        if (button.getCommandId() == 0) throw new CommandNotFoundException(new Exception("No data is available"));
        Command command = CommandFactory.getCommand(button.getCommandId());
        command.setId(button.getCommandId());
        command.setMessageId(button.getMessageId());
        return command;
    }

    private Command getCommandById(int id) {
        return CommandFactory.getCommand(id);
    }
}
