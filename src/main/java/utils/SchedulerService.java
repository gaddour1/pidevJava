package utils;
import javax.mail.MessagingException;
import java.util.concurrent.*;

public class SchedulerService {
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public void scheduleEmailReminder(String email, String subject, String message, long delay) {
        scheduler.schedule(() -> {
            try {
                EmailSender.sendEmail(email, subject, message);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }, delay, TimeUnit.MILLISECONDS);
    }
}
