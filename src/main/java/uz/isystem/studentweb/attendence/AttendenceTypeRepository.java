package uz.isystem.studentweb.attendence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttendenceTypeRepository extends JpaRepository<AttendenceType, Integer> {

}
