package mage.cards.v;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.keyword.ManifestDreadEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ValgavothsOnslaught extends CardImpl {

    public ValgavothsOnslaught(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{X}{G}");

        // Manifest dread X times, then put X +1/+1 counters on each of those creatures.
        this.getSpellAbility().addEffect(new ValgavothsOnslaughtEffect());
    }

    private ValgavothsOnslaught(final ValgavothsOnslaught card) {
        super(card);
    }

    @Override
    public ValgavothsOnslaught copy() {
        return new ValgavothsOnslaught(this);
    }
}

class ValgavothsOnslaughtEffect extends OneShotEffect {

    ValgavothsOnslaughtEffect() {
        super(Outcome.Benefit);
        staticText = "manifest dread X times, then put X +1/+1 counters on each of those creatures";
    }

    private ValgavothsOnslaughtEffect(final ValgavothsOnslaughtEffect effect) {
        super(effect);
    }

    @Override
    public ValgavothsOnslaughtEffect copy() {
        return new ValgavothsOnslaughtEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        int amount = CardUtil.getSourceCostsTag(game, source, "X", 0);
        if (player == null || amount < 1) {
            return false;
        }
        List<Permanent> permanents = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            permanents.add(ManifestDreadEffect.doManifestDread(player, source, game));
            game.processAction();
        }
        permanents.removeIf(Objects::isNull);
        for (Permanent permanent : permanents) {
            permanent.addCounters(CounterType.P1P1.createInstance(amount), source, game);
        }
        return true;
    }
}
