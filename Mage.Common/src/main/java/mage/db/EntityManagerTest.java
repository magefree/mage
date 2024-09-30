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

    public static void main(String[] args) throws Exception {
        List<Feedback> feedbackList = EntityManager.instance.getAllFeedbacks();
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
