
package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.TargetSpell;

import java.util.UUID;

/**
 * @author Loki
 */
public final class CorruptedResolve extends CardImpl {

    public CorruptedResolve(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");


        // Counter target spell if its controller is poisoned.
        this.getSpellAbility().addTarget(new TargetSpell());
        this.getSpellAbility().addEffect(new CorruptedResolveEffect());
    }

    private CorruptedResolve(final CorruptedResolve card) {
        super(card);
    }

    @Override
    public CorruptedResolve copy() {
        return new CorruptedResolve(this);
    }
}

class CorruptedResolveEffect extends OneShotEffect {
    CorruptedResolveEffect() {
        super(Outcome.Detriment);
        staticText = "Counter target spell if its controller is poisoned";
    }

    private CorruptedResolveEffect(final CorruptedResolveEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell = game.getStack().getSpell(getTargetPointer().getFirst(game, source));
        if (spell != null) {
            Player player = game.getPlayer(spell.getControllerId());
            if (player != null && player.getCountersCount(CounterType.POISON) > 0) {
                return game.getStack().counter(getTargetPointer().getFirst(game, source), source, game);
            }
        }
        return false;
    }

    @Override
    public CorruptedResolveEffect copy() {
        return new CorruptedResolveEffect(this);
    }
}
