package frankito.net.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Rating extends JpaRepository<Rating,Long> {
}
