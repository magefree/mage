package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.HasteAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author LoneFox
 */
public final class SoldierHasteToken extends TokenImpl {

    public SoldierHasteToken() {
        super("Soldier Token", "1/1 red and white Soldier creature token with haste");
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        color.setRed(true);
        subtype.add(SubType.SOLDIER);
        power = new MageInt(1);
        toughness = new MageInt(1);
        addAbility(HasteAbility.getInstance());
    }

    protected SoldierHasteToken(final SoldierHasteToken token) {
        super(token);
    }

    @Override
    public SoldierHasteToken copy() {
        return new SoldierHasteToken(this);
    }
}
