
package mage.cards.i;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class InspiringCall extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("creature you control with a +1/+1 counter on it");

    static {
        filter.add(CounterType.P1P1.getPredicate());
    }

    public InspiringCall(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{G}");

        // Draw a card for each creature you control with a +1/+1 counter on it. Those creatures gain indestructible until end of turn. <i>(Damage and effects that say "destroy" don't destroy them.)
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(new PermanentsOnBattlefieldCount(filter)));
        Effect effect = new GainAbilityControlledEffect(IndestructibleAbility.getInstance(), Duration.EndOfTurn, filter);
        effect.setText("Those creatures gain indestructible until end of turn. <i>(Damage and effects that say \"destroy\" don't destroy them.)");
        this.getSpellAbility().addEffect(effect);
    }

    public InspiringCall(final InspiringCall card) {
        super(card);
    }

    @Override
    public InspiringCall copy() {
        return new InspiringCall(this);
    }
}
