package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.BandsWithOtherAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author L_J
 */
public final class WolvesOfTheHuntToken extends TokenImpl {

    public WolvesOfTheHuntToken() {
        super("Wolves of the Hunt", "1/1 green Wolf creature token named Wolves of the Hunt. It has \"bands with other creatures named Wolves of the Hunt.\"");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.WOLF);
        color.setGreen(true);
        power = new MageInt(1);
        toughness = new MageInt(1);
        this.addAbility(new BandsWithOtherAbility("Wolves of the Hunt"));
    }

    public WolvesOfTheHuntToken(final WolvesOfTheHuntToken token) {
        super(token);
    }

    public WolvesOfTheHuntToken copy() {
        return new WolvesOfTheHuntToken(this);
    }
}
