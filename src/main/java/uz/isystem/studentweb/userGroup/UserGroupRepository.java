package uz.isystem.studentweb.userGroup;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserGroupRepository extends JpaRepository<UserGroup, Integer> {
    Optional<UserGroup> findByUserIdAndGroupIdAndDeletedAtIsNull(Integer userid, Integer groupId);

    List<UserGroup> findAllByDeletedAtIsNull();

    List<UserGroup> findAllByGroupIdAndDeletedAtIsNull(Integer groupdId);

    List<UserGroup> findAllByUserIdAndDeletedAtIsNull(Integer id);
}
