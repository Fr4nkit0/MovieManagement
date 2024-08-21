package management.application.exceptions;

public class DuplicateRatingException extends  RuntimeException{
    private String username;
    private Long movieId;

    public DuplicateRatingException(String username, Long movieId) {
        this.username = username;
        this.movieId = movieId;
    }

    @Override
    public String getMessage() {
        return "Rating already submitted for movie whit ID " +movieId+
                " by user "+username+". Only one Rating per user per movie is allowed" ;
    }
}
