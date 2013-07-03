/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */

package mage.cards;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.SpellAbilityType;
import mage.constants.Zone;
import mage.abilities.Abilities;
import mage.abilities.AbilitiesImpl;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.game.Game;
import mage.watchers.Watcher;

/**
 *
 * @author LevelX2
 */

public abstract class SplitCard<T extends SplitCard<T>> extends CardImpl<T> {

    protected Card leftHalfCard;
    protected Card rightHalfCard;

    public SplitCard(UUID ownerId, int cardNumber, String nameLeft, String nameRight, Rarity rarity, CardType[] cardTypes, String costsLeft, String costsRight, boolean fused) {
        super(ownerId, cardNumber, new StringBuilder(nameLeft).append(" // ").append(nameRight).toString(), rarity, cardTypes, costsLeft + costsRight, (fused ?SpellAbilityType.SPLIT_FUSED:SpellAbilityType.SPLIT));
        this.createLeftHalfCard(nameLeft, costsLeft);
        this.createRightHalfCard(nameRight, costsRight);
        this.splitCard = true;
    }

    public SplitCard(SplitCard card) {
        super(card);
        this.leftHalfCard = card.leftHalfCard.copy();
        this.rightHalfCard = card.rightHalfCard.copy();
    }

    private Card createLeftHalfCard (String nameLeft, String costsLeft) {
        CardType[] cardTypes = new CardType[getCardType().size()];
        this.getCardType().toArray(cardTypes);
        leftHalfCard = new LeftHalfCard(this.getOwnerId(), this.getCardNumber(), nameLeft, this.rarity, cardTypes, costsLeft, this);
        //leftHalfCard.getAbilities().setSourceId(objectId);
        return leftHalfCard;
    }

    private Card createRightHalfCard (String nameRight, String costsRight) {
        CardType[] cardTypes = new CardType[getCardType().size()];
        this.getCardType().toArray(cardTypes);
        rightHalfCard = new RightHalfCard(this.getOwnerId(), this.getCardNumber(), nameRight, this.rarity, cardTypes, costsRight, this);
        //rightHalfCard.getAbilities().setSourceId(objectId);
        return rightHalfCard;
    }



    public Card getLeftHalfCard () {
        return leftHalfCard;
    }

    public Card getRightHalfCard () {
        return rightHalfCard;
    }

    @Override
    public boolean cast(Game game, Zone fromZone, SpellAbility ability, UUID controllerId) {
        switch(ability.getSpellAbilityType()) {
            case SPLIT_LEFT:
                return this.getLeftHalfCard().cast(game, fromZone, ability, controllerId);
            case SPLIT_RIGHT:
                return this.getRightHalfCard().cast(game, fromZone, ability, controllerId);
            default:
                return super.cast(game, fromZone, ability, controllerId);
        }
    }
    
    @Override
    public Abilities<Ability> getAbilities(){
        Abilities<Ability> allAbilites = new AbilitiesImpl<Ability>();
        for (Ability ability : super.getAbilities()) {
            if (ability instanceof SpellAbility && !((SpellAbility)ability).getSpellAbilityType().equals(SpellAbilityType.SPLIT)) {
                allAbilites.add(ability);
            }
        }
        allAbilites.addAll(leftHalfCard.getAbilities());
        allAbilites.addAll(rightHalfCard.getAbilities());                
        return allAbilites;
    }

    @Override
    public List<String> getRules() {
        List<String> rules = new ArrayList<String>();
//        rules.addAll(leftHalfCard.getRules());
//        rules.addAll(rightHalfCard.getRules());
        if (getSpellAbility().getSpellAbilityType().equals(SpellAbilityType.SPLIT_FUSED)) {
            rules.add("--------------------------------------------------------------------------\nFuse (You may cast one or both halves of this card from your hand.)");
        }
        return rules;
    }

    @Override
    public void setControllerId(UUID controllerId) {
        abilities.setControllerId(controllerId);
        leftHalfCard.getAbilities().setControllerId(controllerId);
        rightHalfCard.getAbilities().setControllerId(controllerId);
    }

    @Override
    public void setOwnerId(UUID ownerId) {
        this.ownerId = ownerId;
        abilities.setControllerId(ownerId);
        leftHalfCard.getAbilities().setControllerId(ownerId);
        leftHalfCard.setOwnerId(ownerId);
        rightHalfCard.getAbilities().setControllerId(ownerId);
        rightHalfCard.setOwnerId(ownerId);

    }

    @Override
    public List<Watcher> getWatchers() {
        List<Watcher> allWatchers = new ArrayList<Watcher>();
        allWatchers.addAll(super.getWatchers());
        allWatchers.addAll(leftHalfCard.getWatchers());
        allWatchers.addAll(rightHalfCard.getWatchers());
        return allWatchers;
    }
}

/*
 * The left side card of the split card
 */
class LeftHalfCard  extends CardImpl<LeftHalfCard> {

    SplitCard splitCardParent;

    public LeftHalfCard(UUID ownerId, int cardNumber, String name, Rarity rarity, CardType[] cardTypes, String costs, SplitCard splitCardParent) {
        super(ownerId, cardNumber, name, rarity, cardTypes, costs, SpellAbilityType.SPLIT_LEFT);
        this.splitCardParent = splitCardParent;
    }

    public LeftHalfCard(final LeftHalfCard card) {
        super(card);
        this.splitCardParent = card.splitCardParent;
    }

    @Override
    public LeftHalfCard copy() {
        return new LeftHalfCard(this);
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
    public boolean moveToZone(Zone toZone, UUID sourceId, Game game, boolean flag) {
        return splitCardParent.moveToZone(toZone, sourceId, game, flag, null);
    }

    @Override
    public boolean moveToZone(Zone toZone, UUID sourceId, Game game, boolean flag, ArrayList<UUID> appliedEffects) {
        return splitCardParent.moveToZone(toZone, sourceId, game, flag, appliedEffects);
    }

    @Override
    public boolean moveToExile(UUID exileId, String name, UUID sourceId, Game game) {
        return splitCardParent.moveToExile(exileId, name, sourceId, game, null);
    }

    @Override
    public boolean moveToExile(UUID exileId, String name, UUID sourceId, Game game, ArrayList<UUID> appliedEffects) {
        return splitCardParent.moveToExile(exileId, name, sourceId, game, appliedEffects);
    }
}

/*
 * The right side card of the split card
 */
class RightHalfCard  extends CardImpl<RightHalfCard> {

    SplitCard splitCardParent;
    
    public RightHalfCard(UUID ownerId, int cardNumber, String name, Rarity rarity, CardType[] cardTypes, String costs, SplitCard splitCardParent) {
        super(ownerId, cardNumber, name, rarity, cardTypes, costs, SpellAbilityType.SPLIT_RIGHT);
        this.splitCardParent = splitCardParent;
    }

    public RightHalfCard(final RightHalfCard card) {
        super(card);
        this.splitCardParent = card.splitCardParent;
    }

    @Override
    public RightHalfCard copy() {
        return new RightHalfCard(this);
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
    public boolean moveToZone(Zone toZone, UUID sourceId, Game game, boolean flag) {
        return splitCardParent.moveToZone(toZone, sourceId, game, flag, null);
    }

    @Override
    public boolean moveToZone(Zone toZone, UUID sourceId, Game game, boolean flag, ArrayList<UUID> appliedEffects) {
        return splitCardParent.moveToZone(toZone, sourceId, game, flag, appliedEffects);
    }

    @Override
    public boolean moveToExile(UUID exileId, String name, UUID sourceId, Game game) {
        return splitCardParent.moveToExile(exileId, name, sourceId, game, null);
    }

    @Override
    public boolean moveToExile(UUID exileId, String name, UUID sourceId, Game game, ArrayList<UUID> appliedEffects) {
        return splitCardParent.moveToExile(exileId, name, sourceId, game, appliedEffects);
    }
}
