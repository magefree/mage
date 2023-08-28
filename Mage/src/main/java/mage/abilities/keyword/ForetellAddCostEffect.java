package mage.abilities.keyword;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.cards.*;
import mage.constants.*;
import mage.game.Game;

import java.util.UUID;

public class ForetellAddCostEffect extends ContinuousEffectImpl {

    private final MageObjectReference mor;

    public ForetellAddCostEffect(MageObjectReference mor) {
        super(Duration.EndOfGame, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        this.mor = mor;
        staticText = "Foretold card";
    }

    protected ForetellAddCostEffect(final ForetellAddCostEffect effect) {
        super(effect);
        this.mor = effect.mor;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = mor.getCard(game);
        if (card != null) {
            UUID mainCardId = card.getMainCard().getId();
            if (game.getState().getZone(mainCardId) == Zone.EXILED) {
                String foretellCost = (String) game.getState().getValue(mainCardId.toString() + "Foretell Cost");
                String foretellSplitCost = (String) game.getState().getValue(mainCardId.toString() + "Foretell Split Cost");
                if (card instanceof SplitCard) {
                    if (foretellCost != null) {
                        SplitCardHalf leftHalfCard = ((SplitCard) card).getLeftHalfCard();
                        ForetellCostAbility ability = new ForetellCostAbility(foretellCost);
                        ability.setSourceId(leftHalfCard.getId());
                        ability.setControllerId(source.getControllerId());
                        ability.setSpellAbilityType(leftHalfCard.getSpellAbility().getSpellAbilityType());
                        ability.setAbilityName(leftHalfCard.getName());
                        game.getState().addOtherAbility(leftHalfCard, ability);
                    }
                    if (foretellSplitCost != null) {
                        SplitCardHalf rightHalfCard = ((SplitCard) card).getRightHalfCard();
                        ForetellCostAbility ability = new ForetellCostAbility(foretellSplitCost);
                        ability.setSourceId(rightHalfCard.getId());
                        ability.setControllerId(source.getControllerId());
                        ability.setSpellAbilityType(rightHalfCard.getSpellAbility().getSpellAbilityType());
                        ability.setAbilityName(rightHalfCard.getName());
                        game.getState().addOtherAbility(rightHalfCard, ability);
                    }
                } else if (card instanceof ModalDoubleFacedCard) {
                    if (foretellCost != null) {
                        ModalDoubleFacedCardHalf leftHalfCard = ((ModalDoubleFacedCard) card).getLeftHalfCard();
                        // some MDFC's are land IE: sea gate restoration
                        if (!leftHalfCard.isLand(game)) {
                            ForetellCostAbility ability = new ForetellCostAbility(foretellCost);
                            ability.setSourceId(leftHalfCard.getId());
                            ability.setControllerId(source.getControllerId());
                            ability.setSpellAbilityType(leftHalfCard.getSpellAbility().getSpellAbilityType());
                            ability.setAbilityName(leftHalfCard.getName());
                            game.getState().addOtherAbility(leftHalfCard, ability);
                        }
                    }
                    if (foretellSplitCost != null) {
                        ModalDoubleFacedCardHalf rightHalfCard = ((ModalDoubleFacedCard) card).getRightHalfCard();
                        // some MDFC's are land IE: sea gate restoration
                        if (!rightHalfCard.isLand(game)) {
                            ForetellCostAbility ability = new ForetellCostAbility(foretellSplitCost);
                            ability.setSourceId(rightHalfCard.getId());
                            ability.setControllerId(source.getControllerId());
                            ability.setSpellAbilityType(rightHalfCard.getSpellAbility().getSpellAbilityType());
                            ability.setAbilityName(rightHalfCard.getName());
                            game.getState().addOtherAbility(rightHalfCard, ability);
                        }
                    }
                } else if (card instanceof AdventureCard) {
                    if (foretellCost != null) {
                        Card creatureCard = card.getMainCard();
                        ForetellCostAbility ability = new ForetellCostAbility(foretellCost);
                        ability.setSourceId(creatureCard.getId());
                        ability.setControllerId(source.getControllerId());
                        ability.setSpellAbilityType(creatureCard.getSpellAbility().getSpellAbilityType());
                        ability.setAbilityName(creatureCard.getName());
                        game.getState().addOtherAbility(creatureCard, ability);
                    }
                    if (foretellSplitCost != null) {
                        Card spellCard = ((AdventureCard) card).getSpellCard();
                        ForetellCostAbility ability = new ForetellCostAbility(foretellSplitCost);
                        ability.setSourceId(spellCard.getId());
                        ability.setControllerId(source.getControllerId());
                        ability.setSpellAbilityType(spellCard.getSpellAbility().getSpellAbilityType());
                        ability.setAbilityName(spellCard.getName());
                        game.getState().addOtherAbility(spellCard, ability);
                    }
                } else if (foretellCost != null) {
                    ForetellCostAbility ability = new ForetellCostAbility(foretellCost);
                    ability.setSourceId(card.getId());
                    ability.setControllerId(source.getControllerId());
                    ability.setSpellAbilityType(card.getSpellAbility().getSpellAbilityType());
                    ability.setAbilityName(card.getName());
                    game.getState().addOtherAbility(card, ability);
                }
                return true;
            }
        }
        discard();
        return true;
    }

    @Override
    public ForetellAddCostEffect copy() {
        return new ForetellAddCostEffect(this);
    }
}
