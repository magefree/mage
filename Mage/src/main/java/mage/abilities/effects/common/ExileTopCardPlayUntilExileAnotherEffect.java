package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.constants.AsThoughEffectType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.ExileZone;
import mage.game.Game;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

import java.util.Set;
import java.util.UUID;

/**
 * @author Susucr
 */
public class ExileTopCardPlayUntilExileAnotherEffect extends OneShotEffect {


    public ExileTopCardPlayUntilExileAnotherEffect() {
        this(false, "that card");
    }

    public ExileTopCardPlayUntilExileAnotherEffect(boolean withInterveningIf) {
        this(withInterveningIf, "that card");
    }

    public ExileTopCardPlayUntilExileAnotherEffect(String cardDescriptor) {
        this(false, cardDescriptor);
    }

    public ExileTopCardPlayUntilExileAnotherEffect(boolean withInterveningIf, String cardDescriptor) {
        super(Outcome.DrawCard);
        staticText = makeText(withInterveningIf, cardDescriptor);
    }


    private ExileTopCardPlayUntilExileAnotherEffect(final ExileTopCardPlayUntilExileAnotherEffect effect) {
        super(effect);
    }

    @Override
    public OneShotEffect copy() {
        return new ExileTopCardPlayUntilExileAnotherEffect();
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null || !controller.getLibrary().hasCards()) {
            return false;
        }
        Card card = controller.getLibrary().getFromTop(game);
        if (card == null) {
            return false;
        }
        UUID exileId = CardUtil.getExileZoneId(game, source);
        String exileName = CardUtil.getSourceIdName(game, source);
        controller.moveCardsToExile(card, source, game, true, exileId, exileName);
        game.processAction();
        if (!Zone.EXILED.equals(game.getState().getZone(card.getId()))) {
            return true;
        }
        // Allow the card to be played until it leaves that exile zone.
        ContinuousEffect effect = new ExileTopCardPlayEffect(exileId);
        effect.setTargetPointer(new FixedTarget(card.getMainCard(), game));
        game.addEffect(effect, source);
        // Clean the exile Zone from other cards, that can no longer be played.
        ExileZone exileZone = game.getExile().getExileZone(exileId);
        if (exileZone == null) {
            return true;
        }
        Set<Card> inExileZone = exileZone.getCards(game);
        for (Card cardInExile : inExileZone) {
            if (cardInExile.getMainCard().getId().equals(card.getMainCard().getId())) {
                continue;
            }
            game.getExile().moveToMainExileZone(cardInExile, game);
        }
        return true;
    }

    private String makeText(boolean withInterveningIf, String cardDescriptor) {
        StringBuilder sb = new StringBuilder("exile the top card of your library. ");
        if (withInterveningIf) {
            sb.append("If you do, you may play ");
        } else {
            sb.append("You may play ");
        }
        sb.append(cardDescriptor);
        sb.append(" until you exile another card with {this}.");
        return sb.toString();
    }
}

class ExileTopCardPlayEffect extends AsThoughEffectImpl {

    private final UUID exileId;

    ExileTopCardPlayEffect(UUID exileId) {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.Custom, Outcome.Benefit);
        this.exileId = exileId;
    }

    private ExileTopCardPlayEffect(final ExileTopCardPlayEffect effect) {
        super(effect);
        this.exileId = effect.exileId;
    }

    @Override
    public ExileTopCardPlayEffect copy() {
        return new ExileTopCardPlayEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, UUID affectedControllerId, Game game) {
        Card mainTargetCard = game.getCard(getTargetPointer().getFirst(game, source));
        if (mainTargetCard == null) {
            this.discard();
            return false;
        }
        ExileZone exileZone = game.getExile().getExileZone(exileId);
        if (exileZone == null || !exileZone.contains(mainTargetCard.getId())) {
            // Clean the Continuous effect if the target card is no longer in the exile zone
            this.discard();
            return false;
        }
        Card objectCard = game.getCard(sourceId);
        if (objectCard == null) {
            return false;
        }
        return mainTargetCard.getId().equals(objectCard.getMainCard().getId()) // using main card to work with split/mdfc/adventures
                && affectedControllerId.equals(source.getControllerId());
    }
}