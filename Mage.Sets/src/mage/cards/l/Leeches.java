package mage.cards.l;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author spjspj
 */
public final class Leeches extends CardImpl {

    public Leeches(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{W}{W}");

        // Target player loses all poison counters. Leeches deals that much damage to that player.
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addEffect(new LeechesEffect());
    }

    private Leeches(final Leeches card) {
        super(card);
    }

    @Override
    public Leeches copy() {
        return new Leeches(this);
    }
}

class LeechesEffect extends OneShotEffect {

    public LeechesEffect() {
        super(Outcome.Benefit);
        this.staticText = "Target player loses all poison counters. Leeches deals that much damage to that player";
    }

    private LeechesEffect(final LeechesEffect effect) {
        super(effect);
    }

    @Override
    public LeechesEffect copy() {
        return new LeechesEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {

        Player targetPlayer = game.getPlayer(source.getFirstTarget());
        if (targetPlayer == null) {
            return false;
        }

        int countPoisonCounters = targetPlayer.getCounters().getCount(CounterType.POISON);
        if (countPoisonCounters > 0) {
            targetPlayer.removeCounters(CounterType.POISON.getName(), countPoisonCounters, source, game);
            targetPlayer.damage(countPoisonCounters, source.getSourceId(), source, game);
            return true;
        }
        return false;
    }
}
