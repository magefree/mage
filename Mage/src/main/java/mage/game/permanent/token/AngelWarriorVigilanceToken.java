package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.constants.CardType;
import mage.constants.SubType;

public final class AngelWarriorVigilanceToken extends TokenImpl {

    public AngelWarriorVigilanceToken() {
        super("Angel Warrior Token", "4/4 white Angel Warrior creature token with flying and vigilance");
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        subtype.add(SubType.ANGEL);
        subtype.add(SubType.WARRIOR);
        power = new MageInt(4);
        toughness = new MageInt(4);

        addAbility(FlyingAbility.getInstance());
        addAbility(VigilanceAbility.getInstance());
    }

    private AngelWarriorVigilanceToken(final AngelWarriorVigilanceToken token) {
        super(token);
    }

    @Override
    public AngelWarriorVigilanceToken copy() {
        return new AngelWarriorVigilanceToken(this);
    }
}
