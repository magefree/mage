package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanentSameController;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author LevelX2
 */
public final class Cannibalize extends CardImpl {

    public Cannibalize(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{B}");

        // Choose two target creatures controlled by the same player. Exile one of the creatures and put two +1/+1 counters on the other.
        this.getSpellAbility().addEffect(new CannibalizeEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanentSameController(
                2, 2, StaticFilters.FILTER_PERMANENT_CREATURE, false
        ));
    }

    private Cannibalize(final Cannibalize card) {
        super(card);
    }

    @Override
    public Cannibalize copy() {
        return new Cannibalize(this);
    }
}

class CannibalizeEffect extends OneShotEffect {

    public CannibalizeEffect() {
        super(Outcome.Benefit);
        this.staticText = "Choose two target creatures controlled by the same player. " +
                "Exile one of the creatures and put two +1/+1 counters on the other";
    }

    public CannibalizeEffect(final CannibalizeEffect effect) {
        super(effect);
    }

    @Override
    public CannibalizeEffect copy() {
        return new CannibalizeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        List<Permanent> permanents = this
                .getTargetPointer()
                .getTargets(game, source)
                .stream()
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        switch (permanents.size()) {
            case 0:
                return false;
            case 1:
                Permanent permanent = permanents.get(0);
                if (player.chooseUse(
                        outcome, "Exile " + permanent.getIdName() +
                                " or put two +1/+1 counters on it?", null,
                        "Exile", "Add counters", source, game
                )) {
                    player.moveCards(permanent, Zone.EXILED, source, game);
                } else {
                    permanent.addCounters(CounterType.P1P1.createInstance(2), source, game);
                }
                return true;
        }
        Permanent permanent1 = permanents.get(0);
        Permanent permanent2 = permanents.get(1);
        if (player.chooseUse(
                outcome, "Exile " + permanent1.getIdName() +
                        " or " + permanent2.getIdName() + '?',
                "The other creature will get two +1/+1 counters",
                "Exile " + permanent1.getIdName(),
                "Exile " + permanent2.getIdName(), source, game
        )) {
            player.moveCards(permanent1, Zone.EXILED, source, game);
            permanent2.addCounters(CounterType.P1P1.createInstance(2), source, game);
        } else {
            player.moveCards(permanent2, Zone.EXILED, source, game);
            permanent1.addCounters(CounterType.P1P1.createInstance(2), source, game);
        }
        return true;
    }
}
