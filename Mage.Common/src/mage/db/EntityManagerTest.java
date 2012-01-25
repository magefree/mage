package mage.db;

import mage.db.model.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @author noxx
 */
public class EntityManagerTest {

    private static DateFormat timeFormatter = SimpleDateFormat.getTimeInstance(SimpleDateFormat.FULL);

    public static void main(String[] args) throws Exception {
        EntityManager.instance.testDB();
        List<Log> logs = EntityManager.instance.getAllLogs();
        System.out.println("logs found: " + logs.size());
        for (Log log : logs) {
            System.out.println("   key=" + log.getKey());
            System.out.println("   date=" + timeFormatter.format(log.getCreatedDate()));
            System.out.print("   arguments=[ ");
            if (log.getArguments() != null) {
                for (String argument : log.getArguments()) {
                    System.out.print("arg=" + argument + " ");
                }
            }
            System.out.println("]");
            System.out.println("   --------------");
        }
    }
}
