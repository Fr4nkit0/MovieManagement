package management.domain.repository;

import management.domain.entity.Rating;

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
    @Query("SELECT avg(r.rating) FROM Rating r WHERE r.movieId=?1")
    double avgRatingByMovieId (Long id);
    @Query("SELECT min(r.rating) FROM Rating r WHERE r.movieId=?1")
    int minRatingByMovieId (Long id);
    @Query("SELECT max(r.rating) FROM Rating r WHERE r.movieId=?1")
    int maxRatingByMovieId (Long id);
    @Query("SELECT avg(r.rating) FROM Rating r JOIN r.user u WHERE u.username = ?1")
    double avgRatingByUsername(String username);
    @Query("SELECT min(r.rating) FROM Rating r JOIN r.user u WHERE u.username = ?1")
    int minRatingByUsername(String username);
    @Query("SELECT max(r.rating) FROM Rating r JOIN r.user u WHERE u.username =?1")
    int maxRatingByUsername (String username);
    int countByMovieId(Long id);
    @Query("SELECT count(r) FROM Rating r JOIN r.user u  WHERE u.username = ?1")
    int countByUsername(String username);

}
