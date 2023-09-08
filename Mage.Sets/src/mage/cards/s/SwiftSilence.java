package mage.cards.s;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.players.Player;

/**
 *
 * @author noahg
 */
public final class SwiftSilence extends CardImpl {

    public SwiftSilence(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{W}{U}{U}");
        

        // Counter all other spells. Draw a card for each spell countered this way.
        this.getSpellAbility().addEffect(new SwiftSilenceEffect());
    }

    private SwiftSilence(final SwiftSilence card) {
        super(card);
    }

    @Override
    public SwiftSilence copy() {
        return new SwiftSilence(this);
    }
}

class SwiftSilenceEffect extends OneShotEffect {

    public SwiftSilenceEffect() {
        super(Outcome.Detriment);
        staticText = "Counter all other spells. Draw a card for each spell countered this way.";
    }

    private SwiftSilenceEffect(final SwiftSilenceEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<Spell> spellsToCounter = new LinkedList<>();
        for (StackObject stackObject : game.getStack()) {
            if (stackObject instanceof Spell && !stackObject.getId().equals(source.getSourceObject(game).getId())) {
                spellsToCounter.add((Spell) stackObject);
            }
        }
        int toDraw = 0;
        for (Spell spell : spellsToCounter) {
            if (game.getStack().counter(spell.getId(), source, game)){
                toDraw++;
            }
        }
        Player controller = game.getPlayer(source.getControllerId());
        if (toDraw > 0 && controller != null){
            controller.drawCards(toDraw, source, game);
        }
        return true;
    }

    @Override
    public SwiftSilenceEffect copy() {
        return new SwiftSilenceEffect(this);
    }

}