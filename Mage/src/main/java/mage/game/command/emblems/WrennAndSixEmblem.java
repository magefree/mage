package mage.game.command.emblems;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.keyword.RetraceAbility;
import mage.cards.AdventureCard;
import mage.cards.Card;
import mage.constants.*;
import mage.game.Game;
import mage.game.command.Emblem;
import mage.players.Player;

import java.util.Arrays;
import java.util.UUID;
import mage.cards.ModalDoubleFacesCard;
import mage.cards.ModalDoubleFacesCardHalf;
import mage.cards.SplitCard;
import mage.cards.SplitCardHalf;

/**
 * @author TheElk801
 */
public final class WrennAndSixEmblem extends Emblem {

    public WrennAndSixEmblem() {
        this.setName("Emblem Wrenn");
        this.getAbilities().add(new SimpleStaticAbility(Zone.COMMAND, new WrennAndSixEmblemEffect()));

        availableImageSetCodes = Arrays.asList("MH1", "2X2");
    }
}

class WrennAndSixEmblemEffect extends ContinuousEffectImpl {

    WrennAndSixEmblemEffect() {
        super(Duration.WhileOnBattlefield, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        staticText = "Instant and sorcery cards in your graveyard have retrace.";
    }

    private WrennAndSixEmblemEffect(final WrennAndSixEmblemEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        for (UUID cardId : controller.getGraveyard()) {
            Card card = game.getCard(cardId);
            if (card == null) {
                continue;
            }
            if (card instanceof SplitCard) {
                SplitCardHalf leftHalfCard = ((SplitCard) card).getLeftHalfCard();
                SplitCardHalf rightHalfCard = ((SplitCard) card).getRightHalfCard();
                if (leftHalfCard.isInstantOrSorcery(game)) {
                    Ability ability = new RetraceAbility(leftHalfCard);
                    ability.setSourceId(cardId);
                    ability.setControllerId(leftHalfCard.getOwnerId());
                    game.getState().addOtherAbility(leftHalfCard, ability);
                }
                if (rightHalfCard.isInstantOrSorcery(game)) {
                    Ability ability = new RetraceAbility(rightHalfCard);
                    ability.setSourceId(cardId);
                    ability.setControllerId(rightHalfCard.getOwnerId());
                    game.getState().addOtherAbility(rightHalfCard, ability);
                }
            }
            if (card instanceof ModalDoubleFacesCard) {
                ModalDoubleFacesCardHalf leftHalfCard = ((ModalDoubleFacesCard) card).getLeftHalfCard();
                ModalDoubleFacesCardHalf rightHalfCard = ((ModalDoubleFacesCard) card).getRightHalfCard();
                if (leftHalfCard.isInstantOrSorcery(game)) {
                    Ability ability = new RetraceAbility(leftHalfCard);
                    ability.setSourceId(cardId);
                    ability.setControllerId(leftHalfCard.getOwnerId());
                    game.getState().addOtherAbility(leftHalfCard, ability);
                }
                if (rightHalfCard.isInstantOrSorcery(game)) {
                    Ability ability = new RetraceAbility(rightHalfCard);
                    ability.setSourceId(cardId);
                    ability.setControllerId(rightHalfCard.getOwnerId());
                    game.getState().addOtherAbility(rightHalfCard, ability);
                }
            }
            if (card instanceof AdventureCard) {
                // Adventure cards are castable per https://twitter.com/elishffrn/status/1179047911729946624
                card = ((AdventureCard) card).getSpellCard();
            }
            if (!card.isInstantOrSorcery(game)) {
                continue;
            }
            Ability ability = new RetraceAbility(card);
            ability.setSourceId(cardId);
            ability.setControllerId(card.getOwnerId());
            game.getState().addOtherAbility(card, ability);
        }
        return true;
    }

    @Override
    public WrennAndSixEmblemEffect copy() {
        return new WrennAndSixEmblemEffect(this);
    }
}
