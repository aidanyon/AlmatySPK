package timmy.utils.reminder;

import timmy.config.Bot;
import timmy.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import timmy.utils.reminder.timerTask.Task;

import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;

@Slf4j
public class Reminder {

    private Bot                     bot;
    private Timer                   timer = new Timer(true);
    private ArrayList<SendMessage>  messages;

    public      Reminder(Bot bot) {
        this.bot = bot;
        setMorningTask(9);
    }

    public void setMorningTask(int hour) {
        Date date               = DateUtil.getHour(hour);
        log.info("Next check db task set to ", date);
        Task checkMorningTask   = new Task(bot, this);
        timer.schedule(checkMorningTask, date);
    }
}
