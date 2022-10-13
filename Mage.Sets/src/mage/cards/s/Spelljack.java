
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.asthought.PlayFromNotOwnHandZoneTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.target.TargetSpell;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author emerald000
 */
public final class Spelljack extends CardImpl {

    public Spelljack(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{3}{U}{U}{U}");

        // Counter target spell. If that spell is countered this way, exile it instead of putting it into its owner's graveyard. You may play it without paying its mana cost for as long as it remains exiled.
        this.getSpellAbility().addEffect(new SpelljackEffect());
        this.getSpellAbility().addTarget(new TargetSpell());
    }

    private Spelljack(final Spelljack card) {
        super(card);
    }

    @Override
    public Spelljack copy() {
        return new Spelljack(this);
    }
}

class SpelljackEffect extends OneShotEffect {

    SpelljackEffect() {
        super(Outcome.PlayForFree);
        this.staticText = "Counter target spell. If that spell is countered this way, exile it instead of putting it into its owner's graveyard. You may play it without paying its mana cost for as long as it remains exiled";
    }

    SpelljackEffect(final SpelljackEffect effect) {
        super(effect);
    }

    @Override
    public SpelljackEffect copy() {
        return new SpelljackEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            UUID targetId = targetPointer.getFirst(game, source);
            StackObject stackObject = game.getStack().getStackObject(targetId);
            if (stackObject != null && game.getStack().counter(targetId, source, game, PutCards.EXILED)) {
                Card card = ((Spell) stackObject).getCard();
                if (card != null) {
                    ContinuousEffect effect = new PlayFromNotOwnHandZoneTargetEffect(Zone.EXILED, TargetController.YOU, Duration.Custom, true);
                    effect.setTargetPointer(new FixedTarget(card.getId(), game.getState().getZoneChangeCounter(card.getId())));
                    game.addEffect(effect, source);
                }
            }
            return true;
        }
        return false;
    }
}
