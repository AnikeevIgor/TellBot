package pro.sky.telegrambot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.telegrambot.Model.NotificationTask;

import java.time.LocalDateTime;
import java.util.List;


public interface NotificationRepo extends JpaRepository<NotificationTask, Long> {

    List<NotificationTask> getNotificationTaskByNotificationDate(LocalDateTime time);

}
