package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.Counter;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.util.MultiAmountMessage;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author alexander_novo
 */
public final class GoldberryRiverDaughter extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent("another target permanent you control");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public GoldberryRiverDaughter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[] { CardType.CREATURE }, "{1}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.NYMPH);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // {T}: Move a counter of each kind not on Goldberry, River-Daughter from another target permanent you control onto Goldberry.
        SimpleActivatedAbility abilityFrom = new SimpleActivatedAbility(new GoldberryRiverDaughterFromEffect(),
                new TapSourceCost());
        abilityFrom.addTarget(new TargetPermanent(filter));
        this.addAbility(abilityFrom);

        // {U}, {T}: Move one or more counters from Goldberry onto another target permanent you control. If you do, draw a card.
        SimpleActivatedAbility abilityTo = new SimpleActivatedAbility(new GoldberryRiverDaughterToEffect(),
                new ManaCostsImpl<>("{U}"));
        abilityTo.addCost(new TapSourceCost());
        abilityTo.addTarget(new TargetPermanent(filter));
        this.addAbility(abilityTo);
    }

    private GoldberryRiverDaughter(final GoldberryRiverDaughter card) {
        super(card);
    }

    @Override
    public GoldberryRiverDaughter copy() {
        return new GoldberryRiverDaughter(this);
    }
}

class GoldberryRiverDaughterFromEffect extends OneShotEffect {
    GoldberryRiverDaughterFromEffect() {
        super(Outcome.Neutral);
        staticText = "Move a counter of each kind not on {this} from another target permanent you control onto Goldberry.";
    }

    private GoldberryRiverDaughterFromEffect(final GoldberryRiverDaughterFromEffect effect) {
        super(effect);
    }

    @Override
    public GoldberryRiverDaughterFromEffect copy() {
        return new GoldberryRiverDaughterFromEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent fromPermanent = game.getPermanent(source.getFirstTarget());
        Permanent toPermanent = game.getPermanent(source.getSourceId());

        // Create a set of all of the unique counter types on the target permanent that aren't on Goldberry
        Set<Counter> fromCounters = new HashSet<Counter>(fromPermanent.getCounters(game).values());
        fromCounters.removeAll(toPermanent.getCounters(game).values());

        if (fromPermanent == null
                || toPermanent == null
                || controller == null
                || fromCounters.size() == 0) {
            return false;
        }

        for (Counter counter : fromCounters) {
            fromPermanent.removeCounters(counter.getName(), 1, source, game);
            toPermanent.addCounters(CounterType.findByName(counter.getName()).createInstance(1),
                    source.getControllerId(), source, game);
        }
        return true;
    }
}

class GoldberryRiverDaughterToEffect extends OneShotEffect {
    GoldberryRiverDaughterToEffect() {
        super(Outcome.Neutral);
        staticText = "Move one or more counters from Goldberry onto another target permanent you control. If you do, draw a card.";
    }

    private GoldberryRiverDaughterToEffect(final GoldberryRiverDaughterToEffect effect) {
        super(effect);
    }

    @Override
    public GoldberryRiverDaughterToEffect copy() {
        return new GoldberryRiverDaughterToEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent toPermanent = game.getPermanent(source.getFirstTarget());
        Permanent fromPermanent = game.getPermanent(source.getSourceId());

        if (fromPermanent == null
                || toPermanent == null
                || controller == null
                || fromPermanent.getCounters(game).size() == 0) {
            return false;
        }

        List<Counter> counters = new ArrayList<>(fromPermanent.getCounters(game).values());
        counters.sort((c1, c2) -> c1.getName().compareTo(c2.getName()));

        List<MultiAmountMessage> messages = counters.stream()
                .map(c -> new MultiAmountMessage(c.getName() + " (" + c.getCount() + ")", 0, c.getCount()))
                .collect(Collectors.toList());
        int max = messages.stream().map(m -> m.max).reduce(0, Integer::sum);

        int total;
        List<Integer> choices;
        do {
            choices = controller.getMultiAmountWithIndividualConstraints(Outcome.Neutral, messages, 1,
                    max, MultiAmountType.COUNTERS, game);

            total = choices.stream().reduce(0, Integer::sum);
        } while (total < 1);

        // Move the counters. Make sure some counters were actually moved.
        boolean movedCounters = false;
        for (int i = 0; i < choices.size(); i++) {
            Integer amount = choices.get(i);

            if (amount > 0) {
                String counterName = counters.get(i).getName();

                movedCounters |= toPermanent.addCounters(
                        CounterType.findByName(counterName).createInstance(amount),
                        source,
                        game);
                fromPermanent.removeCounters(counterName, amount, source, game);
                game.informPlayers(
                        controller.getLogName() + "moved " +
                                amount + " " +
                                counterName + " counter" + (amount > 1 ? "s" : "") +
                                " from " + fromPermanent.getLogName() +
                                "to " + toPermanent.getLogName() + ".");
            }
        }

        // If some counters were actually moved, draw a card
        if (movedCounters) {
            controller.drawCards(1, source, game);
        }
        return false;
    }
}