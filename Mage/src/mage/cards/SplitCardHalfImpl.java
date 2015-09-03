/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.cards;

import java.util.ArrayList;
import java.util.UUID;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.SpellAbilityType;
import mage.constants.Zone;
import mage.game.Game;

/**
 *
 * @author LevelX2
 */
public class SplitCardHalfImpl extends CardImpl implements SplitCardHalf {

    SplitCard splitCardParent;

    public SplitCardHalfImpl(UUID ownerId, int cardNumber, String name, Rarity rarity, CardType[] cardTypes, String costs, SplitCard splitCardParent, SpellAbilityType spellAbilityType) {
        super(ownerId, cardNumber, name, rarity, cardTypes, costs, spellAbilityType);
        this.splitCardParent = splitCardParent;
    }

    public SplitCardHalfImpl(final SplitCardHalfImpl card) {
        super(card);
        this.splitCardParent = card.splitCardParent;
    }

    @Override
    public UUID getOwnerId() {
        return splitCardParent.getOwnerId();
    }

    @Override
    public String getImageName() {
        return splitCardParent.getImageName();
    }

    @Override
    public String getExpansionSetCode() {
        return splitCardParent.getExpansionSetCode();
    }

    @Override
    public int getCardNumber() {
        return splitCardParent.getCardNumber();
    }

    @Override
    public boolean moveToZone(Zone toZone, UUID sourceId, Game game, boolean flag, ArrayList<UUID> appliedEffects) {
        return splitCardParent.moveToZone(toZone, sourceId, game, flag, appliedEffects);
    }

    @Override
    public boolean moveToExile(UUID exileId, String name, UUID sourceId, Game game, ArrayList<UUID> appliedEffects) {
        return splitCardParent.moveToExile(exileId, name, sourceId, game, appliedEffects);
    }

    @Override
    public SplitCard getMainCard() {
        return splitCardParent;
    }

    @Override
    public void setZone(Zone zone, Game game) {
        game.setZone(splitCardParent.getId(), zone);
        game.setZone(splitCardParent.getLeftHalfCard().getId(), zone);
        game.setZone(splitCardParent.getRightHalfCard().getId(), zone);
    }

    @Override
    public SplitCardHalf copy() {
        return new SplitCardHalfImpl(this);
    }

    @Override
    public void setParentCard(SplitCard card) {
        this.splitCardParent = card;
    }

}
