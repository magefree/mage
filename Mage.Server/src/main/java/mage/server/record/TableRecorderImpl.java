package mage.server.record;

import mage.game.Table;
import mage.game.Table.TableRecorder;
import mage.game.result.ResultProtos.TableProto;
import mage.server.UserManager;
import org.apache.log4j.Logger;

public class TableRecorderImpl implements TableRecorder {

    private static TableRecorderImpl INSTANCE = new TableRecorderImpl();
    private static final Logger logger = Logger.getLogger(TableRecorderImpl.class);

    public static TableRecorderImpl getInstance() {
        return INSTANCE;
    }

    public void record(Table table) {
        TableProto proto = table.toProto();
        TableRecordRepository.instance.add(new TableRecord(proto, proto.getEndTimeMs()));
        UserManager.getInstance().updateUserHistory();
    }
}
