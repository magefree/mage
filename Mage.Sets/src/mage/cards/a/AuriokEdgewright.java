
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MetalcraftCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 *
 * @author maurer.it_at_gmail.com
 */
public final class AuriokEdgewright extends CardImpl {

    protected static String effectText = "<i>Metalcraft</i> &mdash; Auriok Edgewright has double strike as long as you control three or more artifacts.";

    public AuriokEdgewright(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{W}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        ContinuousEffect effect = new GainAbilitySourceEffect(DoubleStrikeAbility.getInstance(), Duration.WhileOnBattlefield);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(effect, MetalcraftCondition.instance, effectText)));
    }

    public AuriokEdgewright(final AuriokEdgewright card) {
        super(card);
    }

    @Override
    public AuriokEdgewright copy() {
        return new AuriokEdgewright(this);
    }
}
