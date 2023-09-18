package mage.game.permanent.token;

import mage.abilities.token.ClueAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 */
public final class ClueArtifactToken extends TokenImpl {

    public ClueArtifactToken() {
        super("Clue Token", "Clue token");
        cardType.add(CardType.ARTIFACT);
        subtype.add(SubType.CLUE);

        // {2}, Sacrifice this artifact: Draw a card.
        this.addAbility(new ClueAbility(false));
    }

    protected ClueArtifactToken(final ClueArtifactToken token) {
        super(token);
    }

    @Override
    public ClueArtifactToken copy() {
        return new ClueArtifactToken(this);
    }
}
