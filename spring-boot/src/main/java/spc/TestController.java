package spc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.PrintWriter;
import java.io.StringWriter;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private TestRepository testRepository;

    @GetMapping
    public ResponseEntity<?> selectRecords() {
        return new ResponseEntity<>(testRepository.selectRecords(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> insertRecord(@RequestBody RecordObject record) {
        testRepository.insertRecord(record);
        return new ResponseEntity<>("Record added", HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateRecord(@PathVariable("id") String id, @RequestBody RecordObject record) {
        try {
            testRepository.updateRecord(id, record);
            return new ResponseEntity<>("Record updated", HttpStatus.OK);
        } catch (NullPointerException e) {
            return new ResponseEntity<>(stackTrace(e), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRecord(@PathVariable("id") String id) {
        try {
            testRepository.deleteRecord(id);
            return new ResponseEntity<>("Record deleted", HttpStatus.OK);
        } catch (NullPointerException e) {
            return new ResponseEntity<>(stackTrace(e), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public String stackTrace(Exception e) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        e.printStackTrace(printWriter);
        return stringWriter.toString();
    }

}
