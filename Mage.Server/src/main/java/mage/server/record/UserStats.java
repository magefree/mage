package mage.server.record;

import com.google.protobuf.InvalidProtocolBufferException;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import mage.game.result.ResultProtos.UserStatsProto;
import org.apache.log4j.Logger;

@DatabaseTable(tableName = "user_stats")
public class UserStats {

    private static final Logger logger = Logger.getLogger(TableRecord.class);

    @DatabaseField(indexName = "user_name_index", unique = true, id = true)
    protected String userName;

    @DatabaseField(dataType = DataType.BYTE_ARRAY)
    protected byte[] proto;

    @DatabaseField(indexName = "end_time_ms_index")
    protected long endTimeMs;

    public UserStats() {
    }

    public UserStats(UserStatsProto proto, long endTimeMs) {
        this.userName = proto.getName();
        this.proto = proto.toByteArray();
        this.endTimeMs = endTimeMs;
    }

    public UserStatsProto getProto() {
        try {
            return UserStatsProto.parseFrom(this.proto);
        } catch (InvalidProtocolBufferException ex) {
            logger.error("Failed to parse serialized proto", ex);
        }
        return null;
    }

    public long getEndTimeMs() {
        return this.endTimeMs;
    }
}
