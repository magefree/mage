package mage.cards.o;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.PreventAllDamageToSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessAllEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.util.functions.EmptyCopyApplier;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OkoTheTrickster extends CardImpl {

    public OkoTheTrickster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{4}{G}{U}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.OKO);
        this.setStartingLoyalty(4);

        // +1: Put two +1/+1 counters on up to one target creature you control.
        Ability ability = new LoyaltyAbility(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance(2)), 1
        );
        ability.addTarget(new TargetControlledCreaturePermanent(0, 1));
        this.addAbility(ability);

        // 0: Until end of turn, Oko, the Trickster becomes a copy of target creature you control. Prevent all damage that would be dealt to him this turn.
        ability = new LoyaltyAbility(new OkoTheTricksterCopyEffect(), 0);
        ability.addEffect(new PreventAllDamageToSourceEffect(Duration.EndOfTurn)
                .setText("Prevent all damage that would be dealt to him this turn"));
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);

        // −7: Until end of turn, each creature you control has base power and toughness 10/10 and gains trample.
        ability = new LoyaltyAbility(new SetBasePowerToughnessAllEffect(
                10, 10, Duration.EndOfTurn, StaticFilters.FILTER_CONTROLLED_CREATURE, true
        ).setText("Until end of turn, each creature you control has base power and toughness 10/10"), -7);
        ability.addEffect(new GainAbilityAllEffect(
                TrampleAbility.getInstance(), Duration.EndOfTurn,
                StaticFilters.FILTER_CONTROLLED_CREATURE
        ).setText("and gains trample"));
        this.addAbility(ability);
    }

    private OkoTheTrickster(final OkoTheTrickster card) {
        super(card);
    }

    @Override
    public OkoTheTrickster copy() {
        return new OkoTheTrickster(this);
    }
}

class OkoTheTricksterCopyEffect extends OneShotEffect {

    OkoTheTricksterCopyEffect() {
        super(Outcome.Copy);
        this.staticText = "Until end of turn, {this} becomes a copy of target creature you control";
    }

    private OkoTheTricksterCopyEffect(final OkoTheTricksterCopyEffect effect) {
        super(effect);
    }

    @Override
    public OkoTheTricksterCopyEffect copy() {
        return new OkoTheTricksterCopyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        Permanent copyFromPermanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (sourcePermanent == null || copyFromPermanent == null) {
            return false;
        }
        game.copyPermanent(Duration.EndOfTurn, copyFromPermanent, sourcePermanent.getId(), source, new EmptyCopyApplier());
        return true;
    }
}
