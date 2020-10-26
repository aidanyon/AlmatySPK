package timmy.command.impl;

import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import timmy.command.Command;
import timmy.entity.custom.Question;
import timmy.entity.enums.WaitingType;
import timmy.entity.standart.User;
import timmy.utils.Const;


public class id005_Question extends Command {
    private Question question = new Question();
    private User user;

    @Override
    public boolean execute()       throws TelegramApiException {
        switch (waitingType) {
            case START:
                user = userDao.getUserByChatId(chatId);
                question.setFullName(user.getFullName());
                question.setContact(user.getPhone());
                question.setEmail(user.getEmail());
                question.setCompany(user.getCompany());
                question.setDepartment(user.getDepartment());
                sendMessage(5008);
                waitingType = WaitingType.SET_COMPLAINT;
                return COMEBACK;
            case SET_COMPLAINT:
                if (hasMessageText()) {
                    question.setQuestion(updateMessageText);
                    questionDao.insert(question);
                    sendMessage(Const.QUESTION_DONE_MESSAGE);
                    return EXIT;
                } else {
                    wrongData();
                    getComplaint();
                }
                return COMEBACK;
        }
        return EXIT;
    }

    private int     getComplaint()  throws TelegramApiException { return botUtils.sendMessage(Const.COMPLAINT_SEND_MESSAGE, chatId); }

    private int     wrongData()     throws TelegramApiException { return botUtils.sendMessage(Const.WRONG_DATA_TEXT, chatId); }

}