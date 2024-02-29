package mage.players;

/**
 * Server: all possible player types
 * <p>
 * Warning, do not change description - it must be same with config.xml file
 *
 * @author IGOUDT
 */
public enum PlayerType {
    HUMAN("Human"),
    COMPUTER_DRAFT_BOT("Computer - draftbot"),
    COMPUTER_MONTE_CARLO("Computer - monte carlo"),
    COMPUTER_MAD("Computer - mad");

    final String description;

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
        throw new IllegalArgumentException(String.format("PlayerType (%s) is not configured in server's config.xml", description));
    }
}
