package timmy.command.impl;

import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import timmy.command.Command;
import timmy.entity.custom.Application;
import timmy.entity.enums.WaitingType;
import timmy.entity.standart.User;
import timmy.services.MailService;
import timmy.utils.Const;

public class id006_Application extends Command {
    private Application application = new Application();
    private User user;

    @Override
    public boolean execute() throws TelegramApiException {
        switch (waitingType){
            case START:
                if (isButton(2026)){
                    application.setLevelId(1);
                    sendMessage(100); }
                if (isButton(8010)){
                    application.setLevelId(2);
                    sendMessage(102);
                }
                if (isButton(8011)){
                    application.setLevelId(3);
                    sendMessage(101); }
                if (isButton(8012)){
                    application.setLevelId(4);
                    sendMessage(101);
                }
                if (isButton(8016)){
                    application.setLevelId(5);
                    sendMessage(103);
                }
                if(isButton(9019)){
                    application.setLevelId(6);
                    sendMessage(100);
                }

                user = userDao.getUserByChatId(chatId);
                application.setFullName(user.getFullName());
                application.setPhoneNumber(user.getPhone());
                application.setEmail(user.getEmail());
                application.setCompany(user.getCompany());
                application.setDepartment(user.getDepartment());
//                sendMessage(100);
                waitingType = WaitingType.APPLICATIONS_SET_ID;
                return COMEBACK;
            case APPLICATIONS_SET_ID:
                if (isButton(5009)){
                    waitingType = WaitingType.APPLICATIONS_SET_COMMENT;
                    sendMessage(6007);
                }
                if (hasMessageText()){
                    application.setRequest(updateMessageText);
                    sendMessage(6007);
                    waitingType = WaitingType.APPLICATIONS_SET_COMMENT;
                }
                return COMEBACK;
            case APPLICATIONS_SET_COMMENT:
                if (isButton(5009)){
                    applicationDao.insert(application);
                }
                if (hasMessageText()){
                    application.setComment(updateMessageText);
                    applicationDao.insert(application);
                }
                String text = String.format(getText(Const.REQUEST_DONE), application.getFullName());
                sendMessage(text);
                MailService.sendMail("kuanyshakhmet7@gmail.com", "Новая заявка");
                return EXIT;
        }
        return EXIT;
    }
}