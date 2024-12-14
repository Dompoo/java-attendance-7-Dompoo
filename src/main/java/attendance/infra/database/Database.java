package attendance.infra.database;

import attendance.infra.entity.DatabaseEntity;

import java.util.List;

public interface Database<T extends DatabaseEntity> {

    List<T> readAll();
}
