package management.service.impl;

import jakarta.persistence.EntityManager;
import management.dto.request.SaveRating;
import management.dto.response.GetCompleteRating;
import management.exceptions.DuplicateRatingException;
import management.exceptions.ResourceNotFoundException;
import management.mapper.RatingMapper;
import management.persistence.entity.Rating;
import management.persistence.entity.User;
import management.persistence.repository.RatingRepository;
import management.service.RatingService;
import management.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class RatingServiceImpl implements RatingService {
    private final RatingRepository ratingRepository;
    private final UserService userService;
    private final EntityManager entityManager;

    public RatingServiceImpl(RatingRepository ratingRepository, UserService userService, EntityManager entityManager) {
        this.ratingRepository = ratingRepository;
        this.userService = userService;
        this.entityManager = entityManager;
    }

    @Override
    public Page<GetCompleteRating> findALl(Pageable pageable) {
        Page<Rating> page = ratingRepository.findAll(pageable);
        if (page.isEmpty()) throw  new ResourceNotFoundException("empty records");
        return  page.map(RatingMapper::toGetCompleteRatingDto);
    }

    @Override
    public Page<GetCompleteRating> findALlMovieId(Long movieId, Pageable pageable) {
        return ratingRepository.findByMovieId(movieId,pageable).map(RatingMapper::toGetCompleteRatingDto);
    }

    @Override
    public Page<GetCompleteRating> findALlUsername(String username, Pageable pageable) {
        return ratingRepository.findByUsername(username,pageable).map(RatingMapper::toGetCompleteRatingDto);
    }

    @Override
    public GetCompleteRating findById(Long ratingId) {
        return RatingMapper.toGetCompleteRatingDto(findByIdEntity(ratingId));
    }

    @Override
    public GetCompleteRating createOne(SaveRating saveRating) {
        if (!ratingRepository.existsByMovieIdAndUserUsername(saveRating.movieId(),saveRating.username())) {
            throw  new DuplicateRatingException(saveRating.username(),saveRating.movieId());}
        User user = userService.findOneByUsernameEntity(saveRating.username());
        Rating createRating= RatingMapper.toEntity(saveRating,user.getId());
        Rating ratingSaved=ratingRepository.save(createRating);
        entityManager.detach(ratingSaved);
        return ratingRepository.findById(ratingSaved.getId())
                .map(RatingMapper::toGetCompleteRatingDto)
                .get();
    }

    @Override
    public GetCompleteRating updateOneById(SaveRating updateRating, Long ratingId) {
        Rating oldRating=findByIdEntity(ratingId);
        User user = userService.findOneByUsernameEntity(updateRating.username());
        RatingMapper.updateEntity(oldRating,updateRating,user.getId());
        return RatingMapper.toGetCompleteRatingDto(ratingRepository.save(oldRating));
    }

    @Override
    public void deleteOneById(Long ratingId) {
        Rating deleteRating = findByIdEntity(ratingId);
        ratingRepository.delete(deleteRating);
    }
    private Rating findByIdEntity(Long id){
        return ratingRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("the id does not exist:"+id));
    }
}
