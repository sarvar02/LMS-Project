package uz.isystem.studentweb.group.group_type;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupTypeRepository extends JpaRepository<GroupType, Integer> {
    List<GroupType> findAllByDeletedAtIsNull();
}
