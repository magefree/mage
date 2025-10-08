package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author LoneFox
 */
public final class HumanCitizenToken extends TokenImpl {

    public HumanCitizenToken() {
        super("Human Token", "1/1 green and white Human Citizen creature token");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        color.setWhite(true);
        subtype.add(SubType.HUMAN);
        subtype.add(SubType.CITIZEN);
        power = new MageInt(1);
        toughness = new MageInt(1);
    }

    private HumanCitizenToken(final HumanCitizenToken token) {
        super(token);
    }

    @Override
    public HumanCitizenToken copy() {
        return new HumanCitizenToken(this);
    }
}
