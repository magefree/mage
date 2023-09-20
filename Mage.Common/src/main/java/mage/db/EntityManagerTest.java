package mage.db;

import mage.db.model.Feedback;
import mage.db.model.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @author noxx
 */
public final class EntityManagerTest {

    private static final DateFormat timeFormatter = SimpleDateFormat.getTimeInstance(SimpleDateFormat.FULL);

    public static void main(String[] args) throws Exception {
        List<Log> logs = EntityManager.INSTANCE.getAllLogs();
        System.out.println("logs found: " + logs.size());
        for (Log log : logs) {
            System.out.println("   key=" + log.getKey());
            System.out.println("   date=" + timeFormatter.format(log.getCreatedDate()));
            System.out.print("   arguments=[ ");
            if (log.getArguments() != null) {
                for (String argument : log.getArguments()) {
                    System.out.print("arg=" + argument + ' ');
                }
            }
            System.out.println("]");
            System.out.println("   --------------");
        }

        System.out.println("********************************");

        List<Feedback> feedbackList = EntityManager.INSTANCE.getAllFeedbacks();
        System.out.println("feedbacks found: " + feedbackList.size());
        int count = 1;
        for (Feedback feedback : feedbackList) {
            System.out.println(count + ". " + feedback.toString());
            System.out.println("message=" + feedback.getMessage());
            System.out.println("mail=" + feedback.getEmail());
            System.out.println("--------------");
            count++;
        }
    }
}
