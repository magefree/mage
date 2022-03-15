package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.VigilanceAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class SoldierVigilanceToken extends TokenImpl {

    public SoldierVigilanceToken() {
        super("Soldier Token", "2/2 white Soldier creature token with vigilance");

        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        subtype.add(SubType.SOLDIER);
        power = new MageInt(2);
        toughness = new MageInt(2);
        addAbility(VigilanceAbility.getInstance());

        setOriginalExpansionSetCode("WAR");
    }

    private SoldierVigilanceToken(final SoldierVigilanceToken token) {
        super(token);
    }

    @Override
    public SoldierVigilanceToken copy() {
        return new SoldierVigilanceToken(this);
    }
}
