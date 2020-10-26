package timmy.command.impl;

import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import timmy.command.Command;
import timmy.entity.custom.Complaint;
import timmy.entity.enums.WaitingType;
import timmy.entity.standart.User;
import timmy.utils.Const;

public class id008_Complaint extends Command {

    private Complaint complaint = new Complaint();
    private User user;

    @Override
    public boolean execute()       throws TelegramApiException {
        switch (waitingType) {
            case START:
                user = userDao.getUserByChatId(chatId);
                complaint.setFullName(user.getFullName());
                complaint.setContact(user.getPhone());
                complaint.setEmail(user.getEmail());
                complaint.setCompany(user.getCompany());
                complaint.setDepartment(user.getDepartment());
                getComplaint();
                waitingType     = WaitingType.SET_COMPLAINT;
                return COMEBACK;
            case SET_COMPLAINT:
                if (hasMessageText()) {
                    complaint.setText(updateMessageText);
                    complaintDao.insert(complaint);
                    String text = String.format(getText(Const.COMPLAINT_DONE_MESSAGE), user.getFullName());
                    sendMessage(text);
                    return EXIT;
                } else {
                    wrongData();
                    getComplaint();
                }
                return COMEBACK;
        }
        return EXIT;
    }

    private int     getComplaint()  throws TelegramApiException { return botUtils.sendMessage(6009, chatId); }

    private int     wrongData()     throws TelegramApiException { return botUtils.sendMessage(Const.WRONG_DATA_TEXT, chatId); }

}