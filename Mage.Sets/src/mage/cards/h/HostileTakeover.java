package mage.cards.h;

import java.util.UUID;

import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.effects.common.continuous.SetPowerToughnessTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.other.AnotherTargetPredicate;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.SecondTargetPointer;

/**
 *
 * @author weirddan455
 */
public final class HostileTakeover extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("other target creature");

    static {
        filter.add(new AnotherTargetPredicate(2));
    }

    public HostileTakeover(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{U}{B}{R}");

        // Up to one target creature has base power and toughness 1/1 until end of turn. Up to one other target creature has base power and toughness 4/4 until end of turn. Then Hostile Takeover deals 3 damage to each creature.
        this.getSpellAbility().addEffect(new SetPowerToughnessTargetEffect(1, 1, Duration.EndOfTurn));
        TargetCreaturePermanent target1 = new TargetCreaturePermanent(0, 1);
        target1.setTargetTag(1);
        this.getSpellAbility().addTarget(target1.withChooseHint("1/1"));

        this.getSpellAbility().addEffect(new SetPowerToughnessTargetEffect(4, 4, Duration.EndOfTurn)
                .setTargetPointer(new SecondTargetPointer())
                .setText("up to one other target creature has base power and toughness 4/4 until end of turn"));
        TargetCreaturePermanent target2 = new TargetCreaturePermanent(0, 1, filter, false);
        target2.setTargetTag(2);
        this.getSpellAbility().addTarget(target2.withChooseHint("4/4"));

        this.getSpellAbility().addEffect(new DamageAllEffect(3, StaticFilters.FILTER_PERMANENT_CREATURE)
                .concatBy("Then"));
    }

    private HostileTakeover(final HostileTakeover card) {
        super(card);
    }

    @Override
    public HostileTakeover copy() {
        return new HostileTakeover(this);
    }
}
