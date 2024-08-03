package frankito.net.persistence.specification;

import frankito.net.dto.request.MovieSearchCriteria;
import frankito.net.persistence.entity.Movie;
import frankito.net.persistence.entity.Rating;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class FindAllMoviesSpecification implements Specification<Movie> {

    private  MovieSearchCriteria movieSearchCriteria;

    public FindAllMoviesSpecification(MovieSearchCriteria movieSearchCriteria) {
        this.movieSearchCriteria = movieSearchCriteria;
    }

    @Override
    public Predicate toPredicate(Root<Movie> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates=new ArrayList<>();
        if (StringUtils.hasText(movieSearchCriteria.title())){
            Predicate titleLike=criteriaBuilder.like(root.get("title"),"%".concat(movieSearchCriteria.title()).concat("%"));
            predicates.add(titleLike);
        }
        if (movieSearchCriteria.minReleaseYear() != null &&movieSearchCriteria.minReleaseYear()>0) {
            Predicate releaseYearGreaterThanEqual=criteriaBuilder
                    .greaterThanOrEqualTo(root.get("releaseYear"),movieSearchCriteria.minReleaseYear());
            predicates.add(releaseYearGreaterThanEqual);
        }
        if (movieSearchCriteria.maxReleaseYear()!= null &&movieSearchCriteria.maxReleaseYear()>0){
            Predicate releaseYearLessThanEqual = criteriaBuilder
                    .lessThanOrEqualTo(root.get("releaseYear"),movieSearchCriteria.maxReleaseYear());
            predicates.add(releaseYearLessThanEqual);
        }
        if (movieSearchCriteria.minAverageRating()!=null && movieSearchCriteria.minAverageRating().doubleValue()>0){
            Subquery<Double> averageRating=getAvarageRatingSubQuery(root, query, criteriaBuilder);
            Predicate averageRatingGreaterThanEqual=criteriaBuilder.greaterThanOrEqualTo(averageRating,
                    movieSearchCriteria.minAverageRating().doubleValue());
            predicates.add(averageRatingGreaterThanEqual);
        }


        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
    private Subquery<Double> getAvarageRatingSubQuery (Root<Movie> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder){
        Subquery<Double> avarageRatingSubQuery=query.subquery(Double.class);
        Root<Rating> ratingRoot = avarageRatingSubQuery.from(Rating.class);

        avarageRatingSubQuery.select(criteriaBuilder.avg(ratingRoot.get("rating")));

        Predicate movieIdEqual=criteriaBuilder.equal(root.get("id"),ratingRoot.get("movieId"));
        avarageRatingSubQuery.where(movieIdEqual);
        return avarageRatingSubQuery;

    }
}
