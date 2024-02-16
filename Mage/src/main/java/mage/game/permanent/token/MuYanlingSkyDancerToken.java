package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.CardType;
import mage.constants.SubType;

public final class MuYanlingSkyDancerToken extends TokenImpl {

    public MuYanlingSkyDancerToken() {
        super("Elemental Bird Token", "4/4 blue Elemental Bird creature token with flying");
        cardType.add(CardType.CREATURE);
        color.setBlue(true);
        subtype.add(SubType.ELEMENTAL);
        subtype.add(SubType.BIRD);
        power = new MageInt(4);
        toughness = new MageInt(4);
        addAbility(FlyingAbility.getInstance());
    }

    private MuYanlingSkyDancerToken(final MuYanlingSkyDancerToken token) {
        super(token);
    }

    public MuYanlingSkyDancerToken copy() {
        return new MuYanlingSkyDancerToken(this);
    }
}
