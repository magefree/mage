
package mage.cards.g;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.AftermathAbility;
import mage.cards.CardSetInfo;
import mage.cards.SplitCard;
import mage.constants.CardType;
import mage.constants.SpellAbilityType;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.CounterPredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class GrindDust extends SplitCard {

    public GrindDust(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, new CardType[]{CardType.SORCERY}, "{1}{B}", "{3}{W}", SpellAbilityType.SPLIT_AFTERMATH);

        // Grind
        // Put a -1/-1 counter on each of up to two target creatures.
        Effect effect = new AddCountersTargetEffect(CounterType.M1M1.createInstance());
        effect.setText("Put a -1/-1 counter on each of up to two target creatures");
        getLeftHalfCard().getSpellAbility().addEffect(effect);
        getLeftHalfCard().getSpellAbility().addTarget(new TargetCreaturePermanent(0, 2));

        // Dust
        // Aftermath
        getRightHalfCard().addAbility(new AftermathAbility().setRuleAtTheTop(true));
        // Exile any number of target creatures that have -1/-1 counters on them.
        getRightHalfCard().getSpellAbility().addEffect(new ExileTargetEffect());
        FilterCreaturePermanent filter = new FilterCreaturePermanent("creatures that have -1/-1 counters on them");
        filter.add(new CounterPredicate(CounterType.M1M1));
        getRightHalfCard().getSpellAbility().addTarget(new TargetCreaturePermanent(0, Integer.MAX_VALUE, filter, false));

    }

    public GrindDust(final GrindDust card) {
        super(card);
    }

    @Override
    public GrindDust copy() {
        return new GrindDust(this);
    }
}
