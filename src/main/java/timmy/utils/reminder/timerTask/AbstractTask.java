package timmy.utils.reminder.timerTask;

import timmy.config.Bot;
import timmy.dao.DaoFactory;
import timmy.dao.impl.ReminderTaskDao;
import timmy.dao.impl.UserDao;
import timmy.utils.reminder.Reminder;

import java.util.TimerTask;

public abstract class AbstractTask extends TimerTask {

    protected Bot               bot;
    protected Reminder          reminder;
    protected DaoFactory        daoFactory;
    protected ReminderTaskDao   reminderTaskDao;
    protected UserDao           userDao;

    public AbstractTask(Bot bot, Reminder reminder) {
        this.bot      = bot;
        this.reminder = reminder;
    }
}
