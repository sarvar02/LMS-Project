package uz.isystem.studentweb.usertype;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserTypeRepository extends JpaRepository<UserType, Integer> {
    List<UserType> findAllByName(String name);
    List<UserType> findAllByDeletedAtIsNull();

    Optional<UserType> findByNameAndDeletedAtIsNull(String name);
}
