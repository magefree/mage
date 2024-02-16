package mage.cards.p;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.target.TargetSpell;

/**
 *
 * @author LevelX2
 */
public final class PsychicStrike extends CardImpl {

    public PsychicStrike(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{U}{B}");

        // Counter target spell. Its controller puts the top two cards of their library into their graveyard.
        this.getSpellAbility().addTarget(new TargetSpell());
        this.getSpellAbility().addEffect(new PsychicStrikeEffect());
    }

    private PsychicStrike(final PsychicStrike card) {
        super(card);
    }

    @Override
    public PsychicStrike copy() {
        return new PsychicStrike(this);
    }
}

class PsychicStrikeEffect extends OneShotEffect {

    PsychicStrikeEffect() {
        super(Outcome.Detriment);
        staticText = "Counter target spell. Its controller mills two cards";
    }

    private PsychicStrikeEffect(final PsychicStrikeEffect effect) {
        super(effect);
    }

    @Override
    public PsychicStrikeEffect copy() {
        return new PsychicStrikeEffect(this);
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
                controller.millCards(2, source, game);
            }
        }
        return countered;
    }
}
