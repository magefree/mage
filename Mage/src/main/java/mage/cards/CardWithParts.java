package mage.cards;

import mage.abilities.Abilities;
import mage.abilities.Ability;
import mage.game.Game;

/**
 * @author TheElk801
 */
public interface CardWithParts extends Card {

    /**
     * The main face of the card for double faced cards, flip cards, and Omen/Adventure cards. The left half of a split card
     * @return the left half card
     */
    Card getLeftHalfCard();

    /**
     * The secondary face of the card for double faced cards, flip cards, and Omen/Adventure cards. The right half of a split card
     * @return the right half card
     */
    Card getRightHalfCard();

    /**
     * Sets the left and right half cards of this card.
     * @param leftHalfCard the left half card
     * @param rightHalfCard the right half card
     */
    void setParts(SubCard<?> leftHalfCard, SubCard<?> rightHalfCard);

    /**
     * Gets the default card side for this card. For single faced cards, this is the card itself.
     * For double faced cards, flip cards, and Omen/Adventure cards, this is the front face.
     * @return the default card side
     */
    default Card getDefaultCardSide() {
        return this;
    }

    /**
     * Gets abilities that are shared across the whole card (not specific to either half).
     * For example, fuse ability on split cards.
     *
     * @param game the current game
     * @return shared abilities
     */
    Abilities<Ability> getSharedAbilities(Game game);

    /**
     * Gets all abilities from all card parts. This ignores rules for MTG rules of actual card abilities.
     * Used to determine castable/playable abilities
     * @return all abilities from all card parts
     */
    Abilities<Ability> getAllAbilities();

    /**
     * Gets all abilities from all card parts. This ignores rules for MTG rules of actual card abilities.
     * Used to determine castable/playable abilities
     * @param game the current game
     * @return all abilities from all card parts
     */
    Abilities<Ability> getAllAbilities(Game game);
}
