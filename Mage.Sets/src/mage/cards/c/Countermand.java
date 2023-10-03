
package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.target.TargetSpell;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class Countermand extends CardImpl {

    public Countermand(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{U}{U}");


        // Counter target spell. Its controller puts the top four cards of their library into their graveyard.
        this.getSpellAbility().addTarget(new TargetSpell(StaticFilters.FILTER_SPELL));
        this.getSpellAbility().addEffect(new CountermandEffect());
    }

    private Countermand(final Countermand card) {
        super(card);
    }

    @Override
    public Countermand copy() {
        return new Countermand(this);
    }
}

class CountermandEffect extends OneShotEffect {

    public CountermandEffect() {
        super(Outcome.Detriment);
        staticText = "Counter target spell. Its controller mills four cards.";
    }

    private CountermandEffect(final CountermandEffect effect) {
        super(effect);
    }

    @Override
    public CountermandEffect copy() {
        return new CountermandEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        boolean countered = false;
        StackObject stackObject = game.getStack().getStackObject(targetPointer.getFirst(game, source));
        if (game.getStack().counter(source.getFirstTarget(), source, game)) {
            countered = true;
        }
        if (stackObject != null) {
            Player controller = game.getPlayer(stackObject.getControllerId());
            if (controller != null) {
                controller.millCards(4, source, game);
            }
        }
        return countered;
    }
}
