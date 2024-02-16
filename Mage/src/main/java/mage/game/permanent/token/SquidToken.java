package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.IslandwalkAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author spjspj
 */
public final class SquidToken extends TokenImpl {

    public SquidToken() {
        super("Squid Token", "1/1 blue Squid creature token with islandwalk");
        cardType.add(CardType.CREATURE);
        color.setBlue(true);
        subtype.add(SubType.SQUID);
        power = new MageInt(1);
        toughness = new MageInt(1);

        this.addAbility(new IslandwalkAbility());
    }

    private SquidToken(final SquidToken token) {
        super(token);
    }

    public SquidToken copy() {
        return new SquidToken(this);
    }
}
