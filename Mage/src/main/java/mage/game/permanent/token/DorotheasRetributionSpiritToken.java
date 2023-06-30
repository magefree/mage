package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author JayDi85
 */
public final class DorotheasRetributionSpiritToken extends TokenImpl {

    public DorotheasRetributionSpiritToken() {
        super("Spirit Token", "4/4 white Spirit creature token with flying");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.SPIRIT);
        color.setWhite(true);
        power = new MageInt(4);
        toughness = new MageInt(4);

        this.addAbility(FlyingAbility.getInstance());
    }

    public DorotheasRetributionSpiritToken(final DorotheasRetributionSpiritToken token) {
        super(token);
    }

    @Override
    public DorotheasRetributionSpiritToken copy() {
        return new DorotheasRetributionSpiritToken(this);
    }
}
