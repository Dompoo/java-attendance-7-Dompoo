package attendance.infra.database;

import attendance.common.CustomExceptions;
import attendance.infra.entity.DatabaseEntity;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class FileDatabase<T extends DatabaseEntity> implements Database<T> {
    
    public FileDatabase() {
        try (BufferedReader reader = new BufferedReader(new FileReader(getFilePath()))) {
            reader.readLine();
        } catch (IOException e) {
            throw CustomExceptions.FILE_NOT_READABLE.get();
        }
    }

    @Override
    public List<T> readAll() {
        try (BufferedReader reader = new BufferedReader(new FileReader(getFilePath()))) {
            return buildObjects(reader);
        } catch (IOException e) {
            throw CustomExceptions.FILE_NOT_READABLE.get();
        }
    }
    
    private List<T> buildObjects(final BufferedReader reader) throws IOException {
        List<T> objects = new ArrayList<>();
        reader.readLine();
        String line;
        while ((line = reader.readLine()) != null && !line.isBlank()) {
            objects.add(convertLineToObject(line));
        }
        return objects;
    }

    protected abstract String getFilePath();

    protected abstract T convertLineToObject(String line);
}
