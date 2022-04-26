package uz.isystem.studentweb.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    List<User> findAllByDeletedAtIsNull();
    Optional<User> findByPhoneAndEmail(String phone, String email);
//    Optional<User> findByPhoneAndIdNotAndDeletedAtIsNull(String phone, Integer id);

    @Query(value = "SELECT * FROM users where id = ?", nativeQuery = true)
    Optional<User> findActiveUser(Integer id);

    @Query("from User where password = :password and phone = :phone")
    @Transactional
    Optional<User> authorize(@Param("password") String password, @Param("phone") String phone);

    Optional<User> findByPhoneAndDeletedAtIsNull(String phone);
}
