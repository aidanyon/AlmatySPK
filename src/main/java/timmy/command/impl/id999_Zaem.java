package timmy.command.impl;

import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import timmy.command.Command;

public class id999_Zaem extends Command {
    @Override
    public boolean execute() throws TelegramApiException {
        sendMessage(7001);
        bot.execute(new SendDocument().setChatId(chatId).setDocument("BQACAgIAAxkBAAIcxl-UJyodaMAAAR1X8yDTmAZzx3bgHAAC-gYAAhEPoEgDXjP8qnhJ6RsE"));
        bot.execute(new SendDocument().setChatId(chatId).setDocument("BQACAgIAAxkBAAIaI1-Svs8lenMJQogOivbyWC_SmI2FAALNCQAC5VmZSBmAWPqZsolnGwQ"));
        return true;
    }
}
