package uz.isystem.studentweb.Class;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClassRepository extends JpaRepository<Class, Integer> {

    Optional<Class> findByIdAndDeletedAtIsNull(Integer id);

    List<Class> findAllByDeletedAtIsNull();

    List<Class> findAllByGroupIdAndDeletedAtIsNull(Integer groupId);

}
