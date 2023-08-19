package mage.game.permanent.token;

import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;
import mage.abilities.keyword.HasteAbility;

/**
 * @author spjspj
 */
public final class GravebornToken extends TokenImpl {

    public GravebornToken() {
        super("Graveborn Token", "3/1 black and red Graveborn creature token with haste");
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        color.setRed(true);
        subtype.add(SubType.GRAVEBORN);
        power = new MageInt(3);
        toughness = new MageInt(1);
        this.addAbility(HasteAbility.getInstance());
    }

    protected GravebornToken(final GravebornToken token) {
        super(token);
    }

    public GravebornToken copy() {
        return new GravebornToken(this);
    }
}
