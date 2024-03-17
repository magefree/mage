package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.WardAbility;
import mage.constants.CardType;

public final class CloakToken extends TokenImpl {

    public CloakToken() {
        super("A Mysterious Creature", "2/2 creature with ward {2}");
        cardType.add(CardType.CREATURE);
        power = new MageInt(2);
        toughness = new MageInt(2);
        addAbility(new WardAbility(new ManaCostsImpl<>("{2}")));
    }

    private CloakToken(final CloakToken token) {
        super(token);
    }

    @Override
    public CloakToken copy() {
        return new CloakToken(this);
    }
}
