

package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MetalcraftCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author Loki, nantuko
 */
public final class SnapsailGlider extends CardImpl {

    protected static String rule = "<i>Metalcraft</i> &mdash; Snapsail Glider has flying as long as you control three or more artifacts";

    public SnapsailGlider (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{3}");
        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        ContinuousEffect effect = new GainAbilitySourceEffect(FlyingAbility.getInstance(), Duration.WhileOnBattlefield);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(effect, MetalcraftCondition.instance, rule)));
    }

    public SnapsailGlider (final SnapsailGlider card) {
        super(card);
    }

    @Override
    public SnapsailGlider copy() {
        return new SnapsailGlider(this);
    }
}
