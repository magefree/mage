package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author Styxo
 */
public final class ShowOfDominance extends CardImpl {

    public ShowOfDominance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{G}");

        // Put four +1/+1 counters on the creature with the highest power. If two or more creatures are tied for the greatest power, you choose one of them. That creature gains trample.
        this.getSpellAbility().addEffect(new ShowOfDominanceEffect());
    }

    private ShowOfDominance(final ShowOfDominance card) {
        super(card);
    }

    @Override
    public ShowOfDominance copy() {
        return new ShowOfDominance(this);
    }
}

class ShowOfDominanceEffect extends OneShotEffect {

    public ShowOfDominanceEffect() {
        super(Outcome.BoostCreature);
        this.staticText = "Put four +1/+1 counters on the creature with the highest power. If two or more creatures are tied for the greatest power, you choose one of them. That creature gains trample until end of turn";
    }

    public ShowOfDominanceEffect(final ShowOfDominanceEffect effect) {
        super(effect);
    }

    @Override
    public ShowOfDominanceEffect copy() {
        return new ShowOfDominanceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int highestPower = Integer.MIN_VALUE;
            Permanent selectedCreature = null;
            for (Permanent permanent : game.getBattlefield().getActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, controller.getId(), game)) {
                if (highestPower < permanent.getPower().getValue()) {
                    highestPower = permanent.getPower().getValue();
                    selectedCreature = permanent;
                } else if (highestPower == permanent.getPower().getValue()) {
                    highestPower = permanent.getPower().getValue();
                    selectedCreature = null;
                }
            }
            if (highestPower != Integer.MIN_VALUE) {
                if (selectedCreature == null) {
                    FilterPermanent filter = new FilterCreaturePermanent("creature with power " + highestPower);
                    filter.add(new PowerPredicate(ComparisonType.EQUAL_TO, highestPower));
                    Target target = new TargetPermanent(1, 1, filter, true);
                    if (controller.chooseTarget(outcome, target, source, game)) {
                        selectedCreature = game.getPermanent(target.getFirstTarget());
                    }
                }
                if (selectedCreature != null) {
                    FixedTarget target = new FixedTarget(selectedCreature.getId(), game);

                    Effect effect = new AddCountersTargetEffect(CounterType.P1P1.createInstance(4));
                    effect.setTargetPointer(target);
                    effect.apply(game, source);

                    ContinuousEffect continuousEffect = new GainAbilityTargetEffect(TrampleAbility.getInstance(), Duration.EndOfTurn);
                    continuousEffect.setTargetPointer(target);
                    game.addEffect(continuousEffect, source);
                    return true;
                }
            }
            return true;
        }
        return false;
    }

}
