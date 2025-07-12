package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TimingRule;
import mage.counters.Counter;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author sobiech
 */
public final class AethericAmplifier extends CardImpl {

    public AethericAmplifier(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // {T}: Add one mana of any color.
        this.addAbility(new AnyColorManaAbility());

        // {4}, {T}: Choose one. Activate only as a sorcery.
        // * Double the number of each kind of counter on target permanent.
        Ability ability = new SimpleActivatedAbility(
                new AethericAmplifierDoublePermanentEffect(), new GenericManaCost(4)
        ).setTiming(TimingRule.SORCERY);
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetPermanent());
        ability.getModes().setChooseText("choose one. Activate only as a sorcery.");

        // * Double the number of each kind of counter you have.
        ability.addMode(new Mode(new AethericAmplifierDoubleControllerEffect()));

        this.addAbility(ability);
    }

    private AethericAmplifier(final AethericAmplifier card) {
        super(card);
    }

    @Override
    public AethericAmplifier copy() {
        return new AethericAmplifier(this);
    }
}

class AethericAmplifierDoublePermanentEffect extends OneShotEffect {

    AethericAmplifierDoublePermanentEffect() {
        super(Outcome.Benefit);
        this.staticText = "double the number of each kind of counter on target permanent";
    }

    private AethericAmplifierDoublePermanentEffect(OneShotEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        final Permanent permanent = game.getPermanent(this.getTargetPointer().getFirst(game, source));

        if (permanent == null) {
            return false;
        }

        final Set<Counter> counters = permanent
                .getCounters(game)
                .values()
                .stream()
                .map(counter -> CounterType
                        .findByName(counter.getName())
                        .createInstance(counter.getCount()))
                .collect(Collectors.toSet());

        if (counters.isEmpty()) {
            return false;
        }

        counters.forEach(counter -> permanent.addCounters(counter, source, game));

        return true;
    }

    @Override
    public AethericAmplifierDoublePermanentEffect copy() {
        return new AethericAmplifierDoublePermanentEffect(this);
    }
}

class AethericAmplifierDoubleControllerEffect extends OneShotEffect {

    AethericAmplifierDoubleControllerEffect() {
        super(Outcome.Benefit);
        this.staticText = "double the number of each kind of counter you have";
    }

    private AethericAmplifierDoubleControllerEffect(OneShotEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        final Player controller = game.getPlayer(source.getControllerId());

        if (controller == null) {
            return false;
        }

        final Set<Counter> counters = controller.getCountersAsCopy()
                .values()
                .stream()
                .map(counter -> CounterType
                        .findByName(counter.getName())
                        .createInstance(counter.getCount()))
                .collect(Collectors.toSet());

        if (counters.isEmpty()) {
            return false;
        }

        counters.forEach(counter -> controller.addCounters(
                counter,
                source.getControllerId(),
                source,
                game));

        return true;
    }

    @Override
    public AethericAmplifierDoubleControllerEffect copy() {
        return new AethericAmplifierDoubleControllerEffect(this);
    }
}
