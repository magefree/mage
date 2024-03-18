package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.TrampleAbility;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;

public class TolsimirMidnightsLightToken extends TokenImpl {

    public TolsimirMidnightsLightToken() {
        super("Voja Fenstalker", "Voja Fenstalker, a legendary 5/5 green and white Wolf creature token with trample");
        this.supertype.add(SuperType.LEGENDARY);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);
        this.color.setGreen(true);
        this.color.setWhite(true);
        this.subtype.add(SubType.WOLF);
        this.cardType.add(CardType.CREATURE);

        addAbility(TrampleAbility.getInstance());
    }

    private TolsimirMidnightsLightToken(final TolsimirMidnightsLightToken token) {
        super(token);
    }

    @Override
    public TolsimirMidnightsLightToken copy() {
        return new TolsimirMidnightsLightToken(this);
    }
}
