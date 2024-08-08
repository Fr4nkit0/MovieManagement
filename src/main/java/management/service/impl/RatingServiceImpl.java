package management.service.impl;

import jakarta.persistence.EntityManager;
import management.dto.request.SaveRating;
import management.dto.response.GetCompleteRating;
import management.dto.response.GetMovie;
import management.dto.response.GetUser;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RatingServiceImpl implements RatingService {
    private final RatingRepository ratingRepository;
    private final UserService userService;
    private final EntityManager entityManager;

    public RatingServiceImpl(RatingRepository ratingRepository, UserService userService, EntityManager entityManager) {
        this.ratingRepository = ratingRepository;
        this.userService = userService;
        this.entityManager = entityManager;
    }
    @Transactional(readOnly = true)
    @Override
    public Page<GetCompleteRating> findALl(Pageable pageable) {
        Page<Rating> page = ratingRepository.findAll(pageable);
        if (page.isEmpty()) throw  new ResourceNotFoundException("empty records");
        return  page.map(RatingMapper::toGetCompleteRatingDto);
    }
    @Transactional(readOnly = true)
    @Override
    public Page<GetMovie.GetRating> findALlMovieId(Long movieId, Pageable pageable) {
        Page<Rating> page=ratingRepository.findByMovieId(movieId,pageable);
        if (page.isEmpty()) throw  new ResourceNotFoundException("empty records");
        return page.map(RatingMapper::toGetMovieRatingDto);
    }
    @Transactional(readOnly = true)
    @Override
    public Page<GetUser.GetRating> findALlUsername(String username, Pageable pageable) {
        Page<Rating> page = ratingRepository.findByUsername(username,pageable);
        if (page.isEmpty()) throw  new ResourceNotFoundException("empty records");
        return ratingRepository.findByUsername(username,pageable).map(RatingMapper::toGetUserRatingDto);
    }
    @Transactional(readOnly = true)
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
