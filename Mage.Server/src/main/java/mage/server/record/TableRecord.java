package mage.server.record;

import com.google.protobuf.InvalidProtocolBufferException;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import mage.game.result.ResultProtos.TableProto;
import org.apache.log4j.Logger;

@DatabaseTable(tableName = "table_history")
public class TableRecord {

    private static final Logger logger = Logger.getLogger(TableRecord.class);

    @DatabaseField(dataType = DataType.BYTE_ARRAY, indexName = "proto_index", unique = true)
    protected byte[] proto;

    @DatabaseField(indexName = "end_time_ms")
    protected long endTimeMs;

    public TableRecord() {
    }

    public TableRecord(TableProto proto, long endTimeMs) {
        this.proto = proto.toByteArray();
        this.endTimeMs = endTimeMs;
    }

    public TableProto getProto() {
        try {
            return TableProto.parseFrom(this.proto);
        } catch (InvalidProtocolBufferException ex) {
            logger.error("Failed to parse serialized proto", ex);
        }
        return null;
    }
}
