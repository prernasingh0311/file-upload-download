package farmmart.file.upload.download.repository;

import farmmart.file.upload.download.entity.File;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ToDoRepository extends CrudRepository<File, Long> {

}
