package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.CounterAnyPredicate;
import mage.filter.predicate.permanent.PermanentIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class SlipperyBogbonder extends CardImpl {

    public SlipperyBogbonder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Hexproof
        this.addAbility(HexproofAbility.getInstance());

        // When Slippery Bogbonder enters the battlefield, put a hexproof counter on target creature. Then move any number of counters from among creatures you control onto that creature.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new AddCountersTargetEffect(CounterType.HEXPROOF.createInstance())
        );
        ability.addEffect(new SlipperyBogbonderEffect());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private SlipperyBogbonder(final SlipperyBogbonder card) {
        super(card);
    }

    @Override
    public SlipperyBogbonder copy() {
        return new SlipperyBogbonder(this);
    }
}

class SlipperyBogbonderEffect extends OneShotEffect {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("creatures to move counters from");

    static {
        filter.add(CounterAnyPredicate.instance);
    }

    SlipperyBogbonderEffect() {
        super(Outcome.Benefit);
        staticText = "Then move any number of counters from among creatures you control onto that creature.";
    }

    private SlipperyBogbonderEffect(final SlipperyBogbonderEffect effect) {
        super(effect);
    }

    @Override
    public SlipperyBogbonderEffect copy() {
        return new SlipperyBogbonderEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent creature = game.getPermanent(source.getFirstTarget());
        FilterPermanent filterPermanent = filter.copy();
        filterPermanent.add(Predicates.not(new PermanentIdPredicate(source.getFirstTarget())));
        if (player == null || creature == null || game.getBattlefield().count(
                filterPermanent, source.getControllerId(), source, game
        ) < 1) {
            return false;
        }
        TargetPermanent target = new TargetPermanent(0, Integer.MAX_VALUE, filterPermanent, true);
        player.choose(outcome, target, source, game);
        List<Permanent> permanents = target
                .getTargets()
                .stream()
                .filter(uuid -> !creature.getId().equals(uuid))
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        if (permanents.isEmpty()) {
            return false;
        }
        Map<UUID, Map<String, Integer>> counterMap = new HashMap<>();
        for (Permanent permanent : permanents) {
            permanent.getCounters(game)
                    .entrySet()
                    .stream()
                    .forEach(entry -> {
                        int num = player.getAmount(
                                0, entry.getValue().getCount(),
                                "Choose how many " + entry.getKey()
                                        + " counters to remove from " + permanent.getLogName(), game
                        );
                        counterMap.computeIfAbsent(
                                permanent.getId(), x -> new HashMap<>()
                        ).put(entry.getKey(), num);
                    });
        }
        for (Permanent permanent : permanents) {
            counterMap
                    .get(permanent.getId())
                    .entrySet()
                    .stream()
                    .filter(entry -> entry.getValue() > 0)
                    .map(entry -> CounterType.findByName(entry.getKey()).createInstance(entry.getValue()))
                    .filter(counter -> creature.addCounters(counter, source.getControllerId(), source, game))
                    .forEach(counter -> permanent.removeCounters(counter, source, game));
        }
        return true;
    }
}
