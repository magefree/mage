package mage.cards.l;

import mage.abilities.condition.common.GiftWasPromisedCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.FightTargetsEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.GiftAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.GiftType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LongstalkBrawl extends CardImpl {

    public LongstalkBrawl(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{G}");

        // Gift a tapped Fish
        this.addAbility(new GiftAbility(this, GiftType.TAPPED_FISH));

        // Choose target creature you control and target creature you don't control. Put a +1/+1 counter on the creature you control if the gift was promised. Then those creatures fight each other.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance()),
                GiftWasPromisedCondition.TRUE, "choose target creature you control and target creature " +
                "you don't control. Put a +1/+1 counter on the creature you control if the gift was promised"
        ));
        this.getSpellAbility().addEffect(new FightTargetsEffect().setText("Then those creatures fight each other"));
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        this.getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_CREATURE_YOU_DONT_CONTROL));
    }

    private LongstalkBrawl(final LongstalkBrawl card) {
        super(card);
    }

    @Override
    public LongstalkBrawl copy() {
        return new LongstalkBrawl(this);
    }
}
