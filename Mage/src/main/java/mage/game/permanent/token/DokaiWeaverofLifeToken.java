package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.common.FilterControlledPermanent;

/**
 * @author spjspj
 */
public final class DokaiWeaverofLifeToken extends TokenImpl {

    static final FilterControlledPermanent filterLands = new FilterControlledLandPermanent("lands you control");

    public DokaiWeaverofLifeToken() {
        super("Elemental Token", "X/X green Elemental creature token, where X is the number of lands you control");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.ELEMENTAL);
        power = new MageInt(0);
        toughness = new MageInt(0);
        DynamicValue controlledLands = new PermanentsOnBattlefieldCount(filterLands);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostSourceEffect(controlledLands, controlledLands, Duration.WhileOnBattlefield)));
    }

    public DokaiWeaverofLifeToken(final DokaiWeaverofLifeToken token) {
        super(token);
    }

    public DokaiWeaverofLifeToken copy() {
        return new DokaiWeaverofLifeToken(this);
    }
}
