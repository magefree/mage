package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.common.CanBlockOnlyFlyingAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.Arrays;

public class OminousRoostBirdToken extends TokenImpl {

    public OminousRoostBirdToken() {
        super("Bird Token", "1/1 blue Bird creature token with flying and \"This creature can block only creatures with flying\"");
        cardType.add(CardType.CREATURE);
        color.setBlue(true);
        subtype.add(SubType.BIRD);
        power = new MageInt(1);
        toughness = new MageInt(1);

        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(new CanBlockOnlyFlyingAbility());

        availableImageSetCodes = Arrays.asList("MID");
    }

    public OminousRoostBirdToken(final OminousRoostBirdToken token) {
        super(token);
    }

    @Override
    public Token copy() {
        return new OminousRoostBirdToken(this);
    }
}
