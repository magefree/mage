package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.common.CanBlockOnlyFlyingAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.CardType;
import mage.constants.SubType;

public class OminousRoostToken extends TokenImpl {
    public OminousRoostToken() {
        super("Bird", "blue Bird creature token with flying and " +
                "\"This creature can block only creatures with flying.\"");
        cardType.add(CardType.CREATURE);
        color.setBlue(true);
        subtype.add(SubType.BIRD);
        power = new MageInt(1);
        toughness = new MageInt(1);

        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(new CanBlockOnlyFlyingAbility());
    }

    private OminousRoostToken(final OminousRoostToken token) {
        super(token);
    }

    @Override
    public OminousRoostToken copy() {
        return new OminousRoostToken(this);
    }
}
