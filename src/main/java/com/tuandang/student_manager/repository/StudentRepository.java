package com.tuandang.student_manager.repository;

import com.tuandang.student_manager.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    // Optional là 1 container chứa giá trị có thể tồn tại hoặc không?
    // giúp xử lý null, tránh lỗi nullPointerException
    Optional<Student> findByEmail(String email);

    // -- Distinct --
    //@Query(value = "select distinct st from Student st where st.firstName = ?1 and st.lastName = ?2")
    List<Student> findDistinctByFirstName(String firstName);

    // -- Or | And --
    @Query(value = "select * from student s where s.firstName=:firstName or s.department=:department", nativeQuery = true)
    Page<Student> findByFirstNameOrDepartment(String firstName, String department);

    // -- Is | Equals --
    //@Query(value = "select s from Student s where s.firstName=:firstName)
    List<Student> findByFirstNameIs(String firstName);
    List<Student> findByFirstNameEquals(String firstName);
    List<Student> findByFirstName(String firstName);

    // -- Between --
    //@Query(value = "select s from Student s where s.id between ?1 and ?2)
    List<Student> findByIdBetween(Long startId, Long endId);

    // -- LessThan | GreaterThan | LessThenEquals | GreaterThanEquals--
    //@Query(value = "select s from Student s where s.id < ?1)
    List<Student> findByIdLessThan(Long id);

    // -- Before | After
    // Sử dụng cho datatype là date

    // -- IsNull | Null | NotNull
    List<Student> findByClassNameNotNull();

    // -- Like | NotLike
    // Lưu ý khi sử dụng hàm phải thêm % tùy trường hợp
    List<Student> findByFirstNameLike(String firstName);

    // -- StartingWith | EndingWith | Containting --
    List<Student> findByFirstNameStartingWith(String name);

    // -- Not --
    List<Student> findByEmailNot(String email);

    // -- In | NotIn--
    // Tìm kiếm trong 1 tập dữ liệu
    List<Student> findByIdIn(Collection<Integer> list);

    // -- True | False
    // Các field boolean
    List<Student> findByGenderTrue();

    // -- IgnoreCase --
    // Không phân biệt chữ hoa chữ thường
    List<Student> findByLastNameIgnoreCase(String lastName);

    // -- OrderBy --
    List<Student> findAllOrderByDepartmentAsc();
}
