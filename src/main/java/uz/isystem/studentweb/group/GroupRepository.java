package uz.isystem.studentweb.group;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.isystem.studentweb.group.Group;

import java.util.List;
import java.util.Optional;

public interface GroupRepository extends JpaRepository<Group, Integer> {
    List<Group> findAllByDeletedAtIsNull();
    Optional<Group> findByName(String name);
}
