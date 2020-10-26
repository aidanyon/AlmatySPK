package timmy;

import lombok.extern.slf4j.Slf4j;
import timmy.config.Bot;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import timmy.utils.reminder.Reminder;

@Slf4j
public class Main {
    private static Reminder reminder;
    public static void main(String[] args) {
        ApiContextInitializer.init();
        log.info("ApiContextInitializer.InitNormal()");
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        Bot bot                         = new Bot();
        reminder                        = new Reminder(bot);
        try {
            telegramBotsApi.registerBot(bot);
            log.info("Bot was registered: " + bot.getBotUsername());
        } catch (TelegramApiRequestException e) {
            log.error("Error in main class", e);
        }
    }
}

