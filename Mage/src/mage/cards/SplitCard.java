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
import mage.abilities.Abilities;
import mage.abilities.AbilitiesImpl;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.SpellAbilityType;
import static mage.constants.SpellAbilityType.SPLIT_LEFT;
import static mage.constants.SpellAbilityType.SPLIT_RIGHT;
import mage.constants.Zone;
import mage.game.Game;

/**
 *
 * @author LevelX2
 */
public abstract class SplitCard extends CardImpl {

    protected Card leftHalfCard;
    protected Card rightHalfCard;

    public SplitCard(UUID ownerId, int cardNumber, String nameLeft, String nameRight, Rarity rarity, CardType[] cardTypes, String costsLeft, String costsRight, boolean fused) {
        super(ownerId, cardNumber, new StringBuilder(nameLeft).append(" // ").append(nameRight).toString(), rarity, cardTypes, costsLeft + costsRight, (fused ? SpellAbilityType.SPLIT_FUSED : SpellAbilityType.SPLIT));
        leftHalfCard = new SplitCardHalfImpl(this.getOwnerId(), this.getCardNumber(), nameLeft, this.rarity, cardTypes, costsLeft, this, SpellAbilityType.SPLIT_LEFT);
        rightHalfCard = new SplitCardHalfImpl(this.getOwnerId(), this.getCardNumber(), nameRight, this.rarity, cardTypes, costsRight, this, SpellAbilityType.SPLIT_RIGHT);
        this.splitCard = true;
    }

    public SplitCard(SplitCard card) {
        super(card);
        this.leftHalfCard = card.leftHalfCard.copy();
        this.rightHalfCard = card.rightHalfCard.copy();
    }

    public Card getLeftHalfCard() {
        return leftHalfCard;
    }

    public Card getRightHalfCard() {
        return rightHalfCard;
    }

    @Override
    public boolean moveToZone(Zone toZone, UUID sourceId, Game game, boolean flag, ArrayList<UUID> appliedEffects) {
        if (super.moveToZone(toZone, sourceId, game, flag, appliedEffects)) {
            game.getState().setZone(getLeftHalfCard().getId(), toZone);
            game.getState().setZone(getRightHalfCard().getId(), toZone);
            return true;
        }
        return false;
    }

    @Override
    public boolean moveToExile(UUID exileId, String name, UUID sourceId, Game game, ArrayList<UUID> appliedEffects) {
        if (super.moveToExile(exileId, name, sourceId, game, appliedEffects)) {
            Zone currentZone = game.getState().getZone(getId());
            game.getState().setZone(getLeftHalfCard().getId(), currentZone);
            game.getState().setZone(getRightHalfCard().getId(), currentZone);
            return true;
        }
        return false;
    }

    @Override
    public boolean cast(Game game, Zone fromZone, SpellAbility ability, UUID controllerId) {
        switch (ability.getSpellAbilityType()) {
            case SPLIT_LEFT:
                return this.getLeftHalfCard().cast(game, fromZone, ability, controllerId);
            case SPLIT_RIGHT:
                return this.getRightHalfCard().cast(game, fromZone, ability, controllerId);
            default:
                return super.cast(game, fromZone, ability, controllerId);
        }
    }

    @Override
    public void setZone(Zone zone, Game game) {
        super.setZone(zone, game);
        game.setZone(getLeftHalfCard().getId(), zone);
        game.setZone(getRightHalfCard().getId(), zone);
    }

    @Override
    public Abilities<Ability> getAbilities() {
        Abilities<Ability> allAbilites = new AbilitiesImpl<>();
        for (Ability ability : super.getAbilities()) {
            if (ability instanceof SpellAbility && !((SpellAbility) ability).getSpellAbilityType().equals(SpellAbilityType.SPLIT)) {
                allAbilites.add(ability);
            }
        }
        allAbilites.addAll(leftHalfCard.getAbilities());
        allAbilites.addAll(rightHalfCard.getAbilities());
        return allAbilites;
    }

    @Override
    public Abilities<Ability> getAbilities(Game game) {
        Abilities<Ability> allAbilites = new AbilitiesImpl<>();
        for (Ability ability : super.getAbilities(game)) {
            if (ability instanceof SpellAbility && !((SpellAbility) ability).getSpellAbilityType().equals(SpellAbilityType.SPLIT)) {
                allAbilites.add(ability);
            }
        }
        allAbilites.addAll(leftHalfCard.getAbilities(game));
        allAbilites.addAll(rightHalfCard.getAbilities(game));
        return allAbilites;
    }

    @Override
    public List<String> getRules() {
        List<String> rules = new ArrayList<>();
        if (getSpellAbility().getSpellAbilityType().equals(SpellAbilityType.SPLIT_FUSED)) {
            rules.add("--------------------------------------------------------------------------\nFuse (You may cast one or both halves of this card from your hand.)");
        }
        return rules;
    }

    @Override
    public void setOwnerId(UUID ownerId) {
        super.setOwnerId(ownerId);
        abilities.setControllerId(ownerId);
        leftHalfCard.getAbilities().setControllerId(ownerId);
        leftHalfCard.setOwnerId(ownerId);
        rightHalfCard.getAbilities().setControllerId(ownerId);
        rightHalfCard.setOwnerId(ownerId);

    }

}
