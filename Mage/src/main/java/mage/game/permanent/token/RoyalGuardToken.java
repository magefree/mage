package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author spjspj
 */
public final class RoyalGuardToken extends TokenImpl {

    public RoyalGuardToken() {
        super("Royal Guard", "2/2 red Soldier creature token with first strike named Royal Guard");
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        cardType.add(CardType.CREATURE);
        color.setRed(true);
        addAbility(FirstStrikeAbility.getInstance());
        subtype.add(SubType.SOLDIER);
    }

    private RoyalGuardToken(final RoyalGuardToken token) {
        super(token);
    }

    public RoyalGuardToken copy() {
        return new RoyalGuardToken(this);
    }
}
