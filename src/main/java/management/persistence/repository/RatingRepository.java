package management.persistence.repository;

import management.persistence.entity.Rating;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingRepository extends JpaRepository<Rating,Long> {

    @Query("SELECT r FROM Rating r JOIN r.user u WHERE u.username = ?1 ")
    Page<Rating> findByUsername (String username , Pageable pageable);
    @Query("SELECT r FROM Rating r JOIN r.movie m WHERE m.id = ?1")
    Page<Rating> findByMovieId (Long movieId,Pageable pageable);

    boolean existsByMovieIdAndUserUsername (Long movieId,String username);

}
