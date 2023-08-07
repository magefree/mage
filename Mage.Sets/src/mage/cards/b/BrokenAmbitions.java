package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ClashEffect;
import mage.abilities.effects.common.CounterUnlessPaysEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.TargetSpell;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class BrokenAmbitions extends CardImpl {

    public BrokenAmbitions(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{X}{U}");

        // Counter target spell unless its controller pays {X}. Clash with an opponent. If you win, that spell's controller puts the top four cards of their library into their graveyard.
        this.getSpellAbility().addEffect(new CounterUnlessPaysEffect(ManacostVariableValue.REGULAR));
        this.getSpellAbility().addEffect(new BrokenAmbitionsEffect());
        this.getSpellAbility().addTarget(new TargetSpell());
    }

    private BrokenAmbitions(final BrokenAmbitions card) {
        super(card);
    }

    @Override
    public BrokenAmbitions copy() {
        return new BrokenAmbitions(this);
    }
}

class BrokenAmbitionsEffect extends OneShotEffect {

    BrokenAmbitionsEffect() {
        super(Outcome.Benefit);
        this.staticText = "Clash with an opponent. If you win, that spell's controller mills four cards";
    }

    private BrokenAmbitionsEffect(final BrokenAmbitionsEffect effect) {
        super(effect);
    }

    @Override
    public BrokenAmbitionsEffect copy() {
        return new BrokenAmbitionsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell = game.getSpellOrLKIStack(source.getFirstTarget());
        if (spell == null) {
            return false;
        }
        Player player = game.getPlayer(spell.getControllerId());
        if (player == null) {
            return false;
        }
        if (new ClashEffect().apply(game, source)) {
            player.millCards(4, source, game);
        }
        return true;
    }
}
