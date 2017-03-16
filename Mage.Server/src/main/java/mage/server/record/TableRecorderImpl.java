package mage.server.record;

import mage.game.Table;
import mage.game.Table.TableRecorder;
import mage.game.result.ResultProtos.TableProto;
import mage.server.UserManager;

public class TableRecorderImpl implements TableRecorder {

    private final static TableRecorderImpl INSTANCE = new TableRecorderImpl();

    public static TableRecorderImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public void record(Table table) {
        TableProto proto = table.toProto();
        TableRecordRepository.instance.add(new TableRecord(proto, proto.getEndTimeMs()));
        UserManager.instance.updateUserHistory();
    }
}
