package mage.cards.a;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.asthought.PlayFromNotOwnHandZoneTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;
import mage.target.targetpointer.FixedTargets;

/**
 *
 * @author Quercitron
 */
public final class ActOnImpulse extends CardImpl {

    public ActOnImpulse(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{R}");

        // Exile the top three cards of your library. Until end of turn, you may play cards exiled this way.
        this.getSpellAbility().addEffect(new ActOnImpulseExileEffect());
    }

    public ActOnImpulse(final ActOnImpulse card) {
        super(card);
    }

    @Override
    public ActOnImpulse copy() {
        return new ActOnImpulse(this);
    }
}

class ActOnImpulseExileEffect extends OneShotEffect {

    public ActOnImpulseExileEffect() {
        super(Outcome.Benefit);
        this.staticText = "Exile the top three cards of your library. Until end of turn, you may play cards exiled this way";
    }

    public ActOnImpulseExileEffect(final ActOnImpulseExileEffect effect) {
        super(effect);
    }

    @Override
    public ActOnImpulseExileEffect copy() {
        return new ActOnImpulseExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (controller != null && sourceObject != null) {
            Set<Card> cards = new HashSet<>(controller.getLibrary().getTopCards(game, 3));
            if (!cards.isEmpty()) {
                controller.moveCardsToExile(cards, source, game, true, source.getSourceId(), sourceObject.getIdName());
                // remove cards that could not be moved to exile
                for (Card card : cards) {
                    if (!Zone.EXILED.equals(game.getState().getZone(card.getId()))) {
                        cards.remove(card);
                    }
                }
                if (!cards.isEmpty()) {
                    ContinuousEffect effect = new PlayFromNotOwnHandZoneTargetEffect(Zone.EXILED, Duration.EndOfTurn);
                    effect.setTargetPointer(new FixedTargets(cards, game));
                    game.addEffect(effect, source);
                }
            }
            return true;
        }
        return false;
    }

}
