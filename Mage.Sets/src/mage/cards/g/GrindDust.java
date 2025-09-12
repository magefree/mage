package mage.cards.g;

import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.AftermathAbility;
import mage.cards.CardSetInfo;
import mage.cards.SplitCard;
import mage.constants.CardType;
import mage.constants.SpellAbilityType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class GrindDust extends SplitCard {

    private static final FilterPermanent filter = new FilterCreaturePermanent("creatures that have -1/-1 counters on them");

    static {
        filter.add(CounterType.M1M1.getPredicate());
    }

    public GrindDust(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, new CardType[]{CardType.SORCERY}, "{1}{B}", "{3}{W}", SpellAbilityType.SPLIT_AFTERMATH);

        // Grind
        // Put a -1/-1 counter on each of up to two target creatures.
        this.getLeftHalfCard().getSpellAbility().addEffect(new AddCountersTargetEffect(CounterType.M1M1.createInstance())
                .setText("Put a -1/-1 counter on each of up to two target creatures"));
        this.getLeftHalfCard().getSpellAbility().addTarget(new TargetCreaturePermanent(0, 2));

        // Dust
        // Aftermath
        this.getRightHalfCard().addAbility(new AftermathAbility().setRuleAtTheTop(true));

        // Exile any number of target creatures that have -1/-1 counters on them.
        this.getRightHalfCard().getSpellAbility().addEffect(new ExileTargetEffect());
        this.getRightHalfCard().getSpellAbility().addTarget(new TargetPermanent(0, Integer.MAX_VALUE, filter));
    }

    private GrindDust(final GrindDust card) {
        super(card);
    }

    @Override
    public GrindDust copy() {
        return new GrindDust(this);
    }
}
