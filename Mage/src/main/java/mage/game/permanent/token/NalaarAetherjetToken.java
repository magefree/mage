package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.CrewAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.CardType;
import mage.constants.SubType;

public class NalaarAetherjetToken extends TokenImpl{

    public NalaarAetherjetToken(int xValue) {
        super("Nalaar Aetherjet", "X/X colorless Vehicle artifact token named Nalaar Aetherjet with flying and crew 2");
        cardType.add(CardType.ARTIFACT);
        subtype.add(SubType.VEHICLE);
        power = new MageInt(xValue);
        toughness = new MageInt(xValue);
        addAbility(FlyingAbility.getInstance());
        addAbility(new CrewAbility(2));
    }

    private NalaarAetherjetToken(final NalaarAetherjetToken token) {
        super(token);
    }

    public NalaarAetherjetToken copy() {
        return new NalaarAetherjetToken(this);
    }
}
