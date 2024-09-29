package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.CardType;
import mage.constants.SubType;

public class Thopter00ColorlessToken extends TokenImpl {

    public Thopter00ColorlessToken() {
        super("Thopter Token", "0/0 colorless Thopter artifact creature token with flying");
        cardType.add(CardType.ARTIFACT);
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.THOPTER);
        power = new MageInt(0);
        toughness = new MageInt(0);

        addAbility(FlyingAbility.getInstance());
    }

    private Thopter00ColorlessToken(final Thopter00ColorlessToken token) {
        super(token);
    }

    @Override
    public Thopter00ColorlessToken copy() {
        return new Thopter00ColorlessToken(this);
    }
}
