package pro.sky.telegrambot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.Model.NotificationTask;
import pro.sky.telegrambot.repositories.NotificationRepo;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    private final Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);


    private final TelegramBot telegramBot;

    private final NotificationRepo notificationRepo;

    public TelegramBotUpdatesListener(TelegramBot telegramBot, NotificationRepo notificationRepo) {
        this.telegramBot = telegramBot;
        this.notificationRepo = notificationRepo;
    }

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }


    @Scheduled(cron = "0 0/1 * * * *")
    public void sendMessageInTime() {
        List<NotificationTask> notiss = new ArrayList<>(notificationRepo.getNotificationTaskByNotificationDate(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES)));
        for (NotificationTask notificationTask : notiss) {
            SendMessage message = new SendMessage(notificationTask.getChatId(), notificationTask.getNotificationText());
            telegramBot.execute(message);
        }
    }

    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
                    logger.info("Processing update: {}", update);
                    Long chatId = update.message().chat().id();
                    String inMessText = update.message().text();

                    if (inMessText.equals("/start")) {
                        SendMessage outMess = new SendMessage(chatId, "Hi");
                        SendResponse response = telegramBot.execute(outMess);
                    }
                    final Pattern pattern = Pattern.compile("([0-9.:\\s]{16})(\\s)([\\W+]+)");
                    final Matcher matcher = pattern.matcher(inMessText);
                    if (matcher.matches()) {
                        String date = matcher.group(1);
                        String text = matcher.group(3);
                        NotificationTask notificationTask = new NotificationTask();
                        notificationTask.setChatId(chatId);
                        notificationTask.setNotificationDate(LocalDateTime.parse(date, DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")));
                        notificationTask.setNotificationText(text);
                        notificationRepo.save(notificationTask);
                        SendMessage outMess2 = new SendMessage(chatId, "notification was add");
                        SendResponse response1 = telegramBot.execute(outMess2);
                    } else {
                        SendMessage outMess1 = new SendMessage(chatId, "Unknown command");
                        SendResponse response2 = telegramBot.execute(outMess1);
                    }
                }
        );
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

}
