package pro.sky.telegrambot.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class NotificationTask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long chatId;
    private String notificationText;
    private LocalDateTime notificationDate;

    public NotificationTask(Long id, Long chatId, String notificationText, LocalDateTime notificationDate) {
        this.id = id;
        this.chatId = chatId;
        this.notificationText = notificationText;
        this.notificationDate = notificationDate;
    }

    public NotificationTask() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public String getNotificationText() {
        return notificationText;
    }

    public void setNotificationText(String notificationText) {
        this.notificationText = notificationText;
    }

    public LocalDateTime getNotificationDate() {
        return notificationDate;
    }

    public void setNotificationDate(LocalDateTime notificationDate) {
        this.notificationDate = notificationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NotificationTask that = (NotificationTask) o;
        return Objects.equals(id, that.id) && Objects.equals(chatId, that.chatId) && Objects.equals(notificationText, that.notificationText) && Objects.equals(notificationDate, that.notificationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, chatId, notificationText, notificationDate);
    }

    @Override
    public String toString() {
        return "notificationTask{" +
                "id=" + id +
                ", chatId=" + chatId +
                ", notificationText='" + notificationText + '\'' +
                ", notificationDate=" + notificationDate +
                '}';
    }
}
