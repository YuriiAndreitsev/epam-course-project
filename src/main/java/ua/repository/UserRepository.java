package ua.repository;

import org.springframework.stereotype.Repository;
import ua.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository <User, Long>{
	Optional<User> getUserById(long id);
	Optional<User> getUsersByEmail(String email);
	List<User> findAllByRole(String role);
}
