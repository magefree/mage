package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.DefenderAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author spjspj
 */
public final class TidalWaveWallToken extends TokenImpl {

    public TidalWaveWallToken() {
        super("Wall Token", "5/5 blue Wall creature token with defender");
        cardType.add(CardType.CREATURE);
        color.setBlue(true);
        subtype.add(SubType.WALL);
        power = new MageInt(5);
        toughness = new MageInt(5);
        this.addAbility(DefenderAbility.getInstance());
    }

    public TidalWaveWallToken(final TidalWaveWallToken token) {
        super(token);
    }

    public TidalWaveWallToken copy() {
        return new TidalWaveWallToken(this);
    }
}
