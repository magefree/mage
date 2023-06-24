package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class SoldierArtifactToken extends TokenImpl {

    public SoldierArtifactToken() {
        super("Soldier Token", "1/1 colorless Soldier artifact creature token");
        cardType.add(CardType.ARTIFACT);
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.SOLDIER);
        power = new MageInt(1);
        toughness = new MageInt(1);
    }

    public SoldierArtifactToken(final SoldierArtifactToken token) {
        super(token);
    }

    public SoldierArtifactToken copy() {
        return new SoldierArtifactToken(this);
    }

}
