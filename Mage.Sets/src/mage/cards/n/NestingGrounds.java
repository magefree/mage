package mage.cards.n;

import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.Counter;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.other.AnotherTargetPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledPermanent;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author anonymous
 */
public final class NestingGrounds extends CardImpl {

    public NestingGrounds(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {1}, {T}: Move a counter from target permanent you control onto another target permanent. Activate this ability only any time you could cast a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(Zone.BATTLEFIELD, new NestingGroundsEffect(), new GenericManaCost(1));
        ability.addCost(new TapSourceCost());
        // target 1
        TargetControlledPermanent target1 = new TargetControlledPermanent(new FilterControlledPermanent("permanent to remove counter from"));
        target1.setTargetTag(1);
        ability.addTarget(target1);
        // target 2
        FilterPermanent filter = new FilterPermanent("permanent to put counter on");
        filter.add(new AnotherTargetPredicate(2));
        TargetPermanent target2 = new TargetPermanent(filter);
        target2.setTargetTag(2);
        ability.addTarget(target2);

        this.addAbility(ability);
    }

    private NestingGrounds(final NestingGrounds card) {
        super(card);
    }

    @Override
    public NestingGrounds copy() {
        return new NestingGrounds(this);
    }
}

class NestingGroundsEffect extends OneShotEffect {

    public NestingGroundsEffect() {
        super(Outcome.AIDontUseIt);
        this.staticText = "Move a counter from target permanent you control onto another target permanent";
    }

    public NestingGroundsEffect(final NestingGroundsEffect effect) {
        super(effect);
    }

    @Override
    public NestingGroundsEffect copy() {
        return new NestingGroundsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent fromPermanent = game.getPermanent(source.getFirstTarget());
        Permanent toPermanent = game.getPermanent(source.getTargets().get(1).getFirstTarget());
        if (fromPermanent == null
                || toPermanent == null
                || controller == null) {
            return false;
        }

        Set<String> possibleChoices = new LinkedHashSet<>(fromPermanent.getCounters(game).keySet());
        if (possibleChoices.size() == 0) {
            return false;
        }

        Choice choice = new ChoiceImpl();
        choice.setChoices(possibleChoices);
        if (controller.choose(outcome, choice, game)) {
            String chosen = choice.getChoice();
            if (fromPermanent.getCounters(game).containsKey(chosen)) {
                CounterType counterType = CounterType.findByName(chosen);
                if (counterType != null) {
                    Counter counter = counterType.createInstance();
                    fromPermanent.removeCounters(counterType.getName(), 1, source, game);
                    toPermanent.addCounters(counter, source.getControllerId(), source, game);
                    return true;
                }
            }
        }
        return false;
    }
}
