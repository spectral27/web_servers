package spc;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

@Component
public class TestRepository {

    private static final List<RecordObject> records = new ArrayList<>();

    public List<RecordObject> selectRecords() {
        return records;
    }

    public void insertRecord(RecordObject record) {
        record.setId(UUID.randomUUID().toString().replace("-", "").substring(0, 16));
        records.add(record);
    }

    public void updateRecord(String id, RecordObject recordObject) {
        boolean recordFound = false;
        for (RecordObject record : records) {
            if (record.getId().equals(id)) {
                record.setOrigin(recordObject.getOrigin());
                recordFound = true;
            }
        }
        if (!recordFound) {
            throw new NullPointerException("Record not found");
        }
    }

    public void deleteRecord(String id) {
        boolean recordFound = false;
        Iterator<RecordObject> iterator = records.listIterator();
        while (iterator.hasNext()) {
            if (iterator.next().getId().equals(id)) {
                iterator.remove();
                recordFound = true;
            }
        }
        if (!recordFound) {
            throw new NullPointerException("Record not found");
        }
    }

}
