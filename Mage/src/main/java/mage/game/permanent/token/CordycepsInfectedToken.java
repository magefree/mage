package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class CordycepsInfectedToken extends TokenImpl {

    public CordycepsInfectedToken() {
        super("Cordyceps Infected", "1/1 black Fungus Zombie creature token named Cordyceps Infected");
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        subtype.add(SubType.FUNGUS);
        subtype.add(SubType.ZOMBIE);
        power = new MageInt(1);
        toughness = new MageInt(1);
    }

    private CordycepsInfectedToken(final CordycepsInfectedToken token) {
        super(token);
    }

    @Override
    public CordycepsInfectedToken copy() {
        return new CordycepsInfectedToken(this);
    }
}
