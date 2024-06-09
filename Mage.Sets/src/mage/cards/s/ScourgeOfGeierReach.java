package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.common.FilterOpponentsCreaturePermanent;

/**
 *
 * @author North
 */
public final class ScourgeOfGeierReach extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterOpponentsCreaturePermanent("creature your opponents control");
    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter);

    public ScourgeOfGeierReach(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}{R}");
        this.subtype.add(SubType.ELEMENTAL);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Scourge of Geier Reach gets +1/+1 for each creature your opponents control.
        this.addAbility(new SimpleStaticAbility(new BoostSourceEffect(xValue, xValue, Duration.WhileOnBattlefield)));
    }

    private ScourgeOfGeierReach(final ScourgeOfGeierReach card) {
        super(card);
    }

    @Override
    public ScourgeOfGeierReach copy() {
        return new ScourgeOfGeierReach(this);
    }
}
