

package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MetalcraftCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author Loki
 */
public final class RazorfieldRhino extends CardImpl {

    public RazorfieldRhino (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{6}");
        this.subtype.add(SubType.RHINO);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);
        ContinuousEffect effect1 = new BoostSourceEffect(2, 2, Duration.WhileOnBattlefield);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(effect1, MetalcraftCondition.instance, "<i>Metalcraft</i> &mdash; Razorfield Rhino gets +2/+2 as long as you control three or more artifacts")));
    }

    public RazorfieldRhino (final RazorfieldRhino card) {
        super(card);
    }

    @Override
    public RazorfieldRhino copy() {
        return new RazorfieldRhino(this);
    }

}
