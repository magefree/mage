package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.HasteAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author spjspj
 */
public final class AkroanSoldierToken extends TokenImpl {

    public AkroanSoldierToken() {
        super("Soldier Token", "1/1 red Soldier creature token with haste");
        cardType.add(CardType.CREATURE);
        color.setRed(true);
        subtype.add(SubType.SOLDIER);
        power = new MageInt(1);
        toughness = new MageInt(1);
        this.addAbility(HasteAbility.getInstance());
    }

    public AkroanSoldierToken(final AkroanSoldierToken token) {
        super(token);
    }

    public AkroanSoldierToken copy() {
        return new AkroanSoldierToken(this);
    }
}
