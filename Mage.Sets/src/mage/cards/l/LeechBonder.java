package mage.cards.l;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.UntapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.Counter;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.other.AnotherTargetPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class LeechBonder extends CardImpl {

    public LeechBonder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");
        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Leech Bonder enters the battlefield with two -1/-1 counters on it.
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.M1M1.createInstance(2)), "with two -1/-1 counters on it"));

        // {U}, {untap}: Move a counter from target creature onto another target creature.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new LeechBonderEffect(), new ManaCostsImpl<>("{U}"));
        ability.addCost(new UntapSourceCost());
        // target 1
        TargetCreaturePermanent target1 = new TargetCreaturePermanent(new FilterCreaturePermanent("creature to remove counter from"));
        target1.setTargetTag(1);
        ability.addTarget(target1);
        // target 2
        FilterCreaturePermanent filter = new FilterCreaturePermanent("creature to put counter on");
        filter.add(new AnotherTargetPredicate(2));
        TargetCreaturePermanent target2 = new TargetCreaturePermanent(filter);
        target2.setTargetTag(2);
        ability.addTarget(target2);

        this.addAbility(ability);

    }

    private LeechBonder(final LeechBonder card) {
        super(card);
    }

    @Override
    public LeechBonder copy() {
        return new LeechBonder(this);
    }
}

class LeechBonderEffect extends OneShotEffect {

    public LeechBonderEffect() {
        super(Outcome.AIDontUseIt);
        this.staticText = "Move a counter from target creature onto a second target creature";
    }

    public LeechBonderEffect(final LeechBonderEffect effect) {
        super(effect);
    }

    @Override
    public LeechBonderEffect copy() {
        return new LeechBonderEffect(this);
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

        Set<String> possibleChoices = new HashSet<>(fromPermanent.getCounters(game).keySet());
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
