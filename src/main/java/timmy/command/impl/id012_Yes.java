package timmy.command.impl;

import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import timmy.command.Command;
import timmy.entity.custom.Operators;
import timmy.entity.enums.WaitingType;
import timmy.entity.standart.User;
import timmy.services.MailService;

public class id012_Yes extends Command {
    private Operators operators = new Operators();
    private User user;

    @Override
    public boolean execute() throws TelegramApiException {
        switch (waitingType) {
            case START:
                if (isButton(2015)) {
                    operators.setLevelId(1);
                }
                if (isButton(8013)) {
                    operators.setLevelId(2);
                }
                if (isButton(8014)) {
                    operators.setLevelId(3);
                }
                if (isButton(8015)) {
                    operators.setLevelId(4);
                }
                user = userDao.getUserByChatId(chatId);
                operators.setFullName(user.getFullName());
                operators.setPhoneNumber(user.getPhone());
                operators.setEmail(user.getEmail());
                operators.setCompany(user.getCompany());
                operators.setDepartment(user.getDepartment());
                sendMessage(5016);
                waitingType = WaitingType.SET_QUESTION;
                return COMEBACK;
            case SET_QUESTION:
                if (hasMessageText()) {
                    operators.setQuestion(updateMessageText);
                    operatorsDao.insert(operators);
                    String text = String.format(getText(5017), operators.getFullName());
                    sendMessage(text);
                    MailService.sendMail("kuanyshakhmet7@gmail.com", "Новый запрос консультации");
                    return EXIT;
                }
//                return EXIT;
        }
        return EXIT;
    }
}

