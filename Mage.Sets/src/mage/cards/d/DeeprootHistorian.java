package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.keyword.RetraceAbility;
import mage.cards.*;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author Grath
 */
public final class DeeprootHistorian extends CardImpl {

    public DeeprootHistorian(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");
        
        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Merfolk and Druid cards in your graveyard have retrace.
        this.addAbility(new SimpleStaticAbility(new DeeprootHistorianEffect()));
    }

    private DeeprootHistorian(final DeeprootHistorian card) {
        super(card);
    }

    @Override
    public DeeprootHistorian copy() {
        return new DeeprootHistorian(this);
    }
}

class DeeprootHistorianEffect extends ContinuousEffectImpl {

    DeeprootHistorianEffect() {
        super(Duration.WhileOnBattlefield, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        staticText = "Merfolk and Druid cards in your graveyard have retrace.";
    }

    private DeeprootHistorianEffect(final DeeprootHistorianEffect effect) {
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
                if (leftHalfCard.hasSubtype(SubType.MERFOLK, game) || leftHalfCard.hasSubtype(SubType.DRUID, game)) {
                    Ability ability = new RetraceAbility(leftHalfCard);
                    ability.setSourceId(cardId);
                    ability.setControllerId(leftHalfCard.getOwnerId());
                    game.getState().addOtherAbility(leftHalfCard, ability);
                }
                if (rightHalfCard.hasSubtype(SubType.MERFOLK, game) || rightHalfCard.hasSubtype(SubType.DRUID, game)) {
                    Ability ability = new RetraceAbility(rightHalfCard);
                    ability.setSourceId(cardId);
                    ability.setControllerId(rightHalfCard.getOwnerId());
                    game.getState().addOtherAbility(rightHalfCard, ability);
                }
            }
            if (card instanceof ModalDoubleFacedCard) {
                ModalDoubleFacedCardHalf leftHalfCard = ((ModalDoubleFacedCard) card).getLeftHalfCard();
                ModalDoubleFacedCardHalf rightHalfCard = ((ModalDoubleFacedCard) card).getRightHalfCard();
                if (leftHalfCard.hasSubtype(SubType.MERFOLK, game) || leftHalfCard.hasSubtype(SubType.DRUID, game)) {
                    Ability ability = new RetraceAbility(leftHalfCard);
                    ability.setSourceId(cardId);
                    ability.setControllerId(leftHalfCard.getOwnerId());
                    game.getState().addOtherAbility(leftHalfCard, ability);
                }
                if (rightHalfCard.hasSubtype(SubType.MERFOLK, game) || rightHalfCard.hasSubtype(SubType.DRUID, game)) {
                    Ability ability = new RetraceAbility(rightHalfCard);
                    ability.setSourceId(cardId);
                    ability.setControllerId(rightHalfCard.getOwnerId());
                    game.getState().addOtherAbility(rightHalfCard, ability);
                }
            }
            if (!card.hasSubtype(SubType.MERFOLK, game) && !card.hasSubtype(SubType.DRUID, game)) {
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
    public DeeprootHistorianEffect copy() {
        return new DeeprootHistorianEffect(this);
    }
}