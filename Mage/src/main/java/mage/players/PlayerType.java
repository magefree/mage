package mage.players;

/**
 * Created by IGOUDT on 2-4-2017.
 */
public enum PlayerType {
    HUMAN("Human"),
    COMPUTER_DRAFT_BOT("Computer - draftbot"),
    COMPUTER_MINIMAX_HYBRID("Computer - minimax hybrid"),
    COMPUTER_MONTE_CARLO("Computer - monte carlo"),
    COMPUTER_MAD("Computer - mad");

    String description;

    PlayerType(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }

    public static PlayerType getByDescription(String description) {
        for (PlayerType type : values()) {
            if (type.description.equals(description)) {
                return type;
            }
        }
        throw new IllegalArgumentException(String.format("PlayerType (%s) is not configured", description));
    }
}
