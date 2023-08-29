package mage.game.permanent.token;

import mage.abilities.token.TreasureAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class TreasureToken extends TokenImpl {

    public TreasureToken() {
        super("Treasure Token", "Treasure token");
        cardType.add(CardType.ARTIFACT);
        subtype.add(SubType.TREASURE);

        this.addAbility(new TreasureAbility(false));
    }

    protected TreasureToken(final TreasureToken token) {
        super(token);
    }

    public TreasureToken copy() {
        return new TreasureToken(this);
    }
}
