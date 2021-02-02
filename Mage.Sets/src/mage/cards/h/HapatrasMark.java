
package mage.cards.h;

import java.util.UUID;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.RemoveAllCountersTargetEffect;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.counters.CounterType;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class HapatrasMark extends CardImpl {

    public HapatrasMark(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{G}");

        // Target creature you control gains hexproof until end of turn. Remove all -1/-1 counters from it.
        getSpellAbility().addEffect(new GainAbilityTargetEffect(HexproofAbility.getInstance(), Duration.EndOfTurn));
        getSpellAbility().addEffect(new RemoveAllCountersTargetEffect(CounterType.M1M1));
        getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
    }

    private HapatrasMark(final HapatrasMark card) {
        super(card);
    }

    @Override
    public HapatrasMark copy() {
        return new HapatrasMark(this);
    }
}
