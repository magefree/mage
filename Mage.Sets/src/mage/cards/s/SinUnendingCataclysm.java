package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.PutSourceCountersOnTargetEffect;
import mage.abilities.effects.common.ShuffleIntoLibrarySourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.Counter;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.CounterAnyPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class SinUnendingCataclysm extends CardImpl {

    public SinUnendingCataclysm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{G}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.LEVIATHAN);
        this.subtype.add(SubType.AVATAR);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // As Sin enters, remove all counters from any number of artifacts, creatures, and enchantments. Sin enters with X +1/+1 counters on it, where X is twice the number of counters removed this way.
        this.addAbility(new AsEntersBattlefieldAbility(new SinUnendingCataclysmEffect()));

        // When Sin dies, put its counters on target creature you control, then shuffle this card into its owner's library.
        Ability ability = new DiesSourceTriggeredAbility(new PutSourceCountersOnTargetEffect());
        ability.addEffect(new ShuffleIntoLibrarySourceEffect().setText(", then shuffle this card into its owner's library"));
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);
    }

    private SinUnendingCataclysm(final SinUnendingCataclysm card) {
        super(card);
    }

    @Override
    public SinUnendingCataclysm copy() {
        return new SinUnendingCataclysm(this);
    }
}

class SinUnendingCataclysmEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterPermanent("artifacts, creatures, and enchantments");

    static {
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.CREATURE.getPredicate(),
                CardType.ENCHANTMENT.getPredicate()
        ));
        filter.add(CounterAnyPredicate.instance);
    }

    SinUnendingCataclysmEffect() {
        super(Outcome.Benefit);
        staticText = "remove all counters from any number of artifacts, creatures, and enchantments. " +
                "{this} enters with X +1/+1 counters on it, where X is twice the number of counters removed this way";
    }

    private SinUnendingCataclysmEffect(final SinUnendingCataclysmEffect effect) {
        super(effect);
    }

    @Override
    public SinUnendingCataclysmEffect copy() {
        return new SinUnendingCataclysmEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        TargetPermanent target = new TargetPermanent(0, Integer.MAX_VALUE, filter, true);
        player.choose(outcome, target, source, game);
        int count = 0;
        for (UUID targetId : target.getTargets()) {
            Permanent permanent = game.getPermanent(targetId);
            if (permanent == null) {
                continue;
            }
            Set<Counter> counters = permanent.getCounters(game)
                    .entrySet()
                    .stream()
                    .map(e -> CounterType.findByName(e.getKey()).createInstance(e.getValue().getCount()))
                    .collect(Collectors.toSet());
            for (Counter counter : counters) {
                count += permanent.removeCounters(counter, source, game);
            }
        }
        if (count < 1) {
            return false;
        }
        Counter counter = CounterType.P1P1.createInstance(2 * count);
        Optional.ofNullable(source)
                .map(Ability::getSourceId)
                .map(game::getPermanentEntering)
                .ifPresent(permanent -> permanent.addCounters(counter, source, game));
        return true;
    }
}
