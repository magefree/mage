package mage.cards.i;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
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
 * @author TheElk801
 */
public final class Ionize extends CardImpl {

    public Ionize(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}{R}");

        // Counter target spell. Ionize deals 2 damage to that spell's controller.
        this.getSpellAbility().addTarget(new TargetSpell());
        this.getSpellAbility().addEffect(new IonizeEffect());
    }

    private Ionize(final Ionize card) {
        super(card);
    }

    @Override
    public Ionize copy() {
        return new Ionize(this);
    }
}

class IonizeEffect extends OneShotEffect {

    public IonizeEffect() {
        super(Outcome.Benefit);
        this.staticText = "Counter target spell. "
                + "{this} deals 2 damage to that spell's controller.";
    }

    private IonizeEffect(final IonizeEffect effect) {
        super(effect);
    }

    @Override
    public IonizeEffect copy() {
        return new IonizeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        boolean result = false;
        Spell spell = game.getStack().getSpell(source.getFirstTarget());
        if (spell != null) {
            Player spellController = game.getPlayer(spell.getControllerId());

            result = game.getStack().counter(source.getFirstTarget(), source, game);
            if (spellController != null) {
                spellController.damage(2, source.getSourceId(), source, game);
            }
        }
        return result;
    }
}
