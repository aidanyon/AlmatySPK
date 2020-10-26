package timmy.command.impl;

import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import timmy.command.Command;
import timmy.entity.custom.Quest;
import timmy.entity.enums.WaitingType;
import timmy.utils.ButtonsLeaf;

import java.util.ArrayList;
import java.util.List;

public class id555_Quest extends Command {

    private List<Quest> questList;
    private ButtonsLeaf buttonsLeaf;
    private List<String> list;

    @Override
    public boolean execute() throws TelegramApiException {
        switch (waitingType){
            case START:
                deleteMessage(updateMessageId);
                sendInfo();
                waitingType = WaitingType.CHOOSE_QUESTION;
                return COMEBACK;
            case CHOOSE_QUESTION:
                deleteMessage(updateMessageId);
                if (hasCallbackQuery()) {
                    Quest quest = questList.get(Integer.parseInt(updateMessageText));
                    toDeleteKeyboard(sendMessageWithKeyboard(quest.getText(),43));
                    waitingType=WaitingType.BACK;
                    return COMEBACK;

                }
                return COMEBACK;
            case BACK:
                deleteMessage(updateMessageId);
                if(isButton(6025)){
                    toDeleteKeyboard(sendMessageWithKeyboard("Список вопросов", buttonsLeaf.getListButton()));
                    waitingType = WaitingType.CHOOSE_QUESTION;
                    return COMEBACK;
                }
                return COMEBACK;
        }
        return EXIT;
    }
    private void sendInfo() throws TelegramApiException {
        questList = factory.getQuestDao().getAll();
        list = new ArrayList<>();
        questList.forEach(quest -> list.add(quest.getName()));
        buttonsLeaf = new ButtonsLeaf(list);
        sendMessageWithKeyboard("Список вопросов", buttonsLeaf.getListButton());
    }
}
