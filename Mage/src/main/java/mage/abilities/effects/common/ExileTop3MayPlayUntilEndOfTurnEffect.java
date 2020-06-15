package mage.abilities.effects.common;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.asthought.PlayFromNotOwnHandZoneTargetEffect;
import mage.cards.Card;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.targetpointer.FixedTargets;

import java.util.HashSet;
import java.util.Set;

public class ExileTop3MayPlayUntilEndOfTurnEffect extends OneShotEffect {

    public ExileTop3MayPlayUntilEndOfTurnEffect() {
        super(Outcome.Benefit);
        this.staticText = "exile the top three cards of your library. Until end of turn, you may play cards exiled this way";
    }

    public ExileTop3MayPlayUntilEndOfTurnEffect(final ExileTop3MayPlayUntilEndOfTurnEffect effect) {
        super(effect);
    }

    @Override
    public ExileTop3MayPlayUntilEndOfTurnEffect copy() {
        return new ExileTop3MayPlayUntilEndOfTurnEffect(this);
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
                cards.removeIf(card -> !Zone.EXILED.equals(game.getState().getZone(card.getId())));
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
