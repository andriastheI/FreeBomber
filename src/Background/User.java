package Background;

/**
 * Represents a user in the game, holding their name and score.
 * This class is commonly used for displaying high scores or player profiles.
 */
public class User {

    /** The name of the user/player. */
    private String name;
    /** The score achieved by the user. */
    private Integer score;

    /**
     * Returns the name of the user.
     *
     * @return the user's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the user.
     *
     * @param name the user's name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the score of the user.
     *
     * @return the user's score.
     */
    public Integer getScore() {
        return score;
    }

    /**
     * Sets the score of the user.
     *
     * @param score the user's score to set.
     */
    public void setScore(Integer score) {
        this.score = score;
    }
}