/*
* Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
*
* Redistribution and use in source and binary forms, with or without modification, are
* permitted provided that the following conditions are met:
*
*    1. Redistributions of source code must retain the above copyright notice, this list of
*       conditions and the following disclaimer.
*
*    2. Redistributions in binary form must reproduce the above copyright notice, this list
*       of conditions and the following disclaimer in the documentation and/or other materials
*       provided with the distribution.
*
* THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
* WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
* FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
* CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
* CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
* SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
* ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
* NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
* ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*
* The views and conclusions contained in the software and documentation are those of the
* authors and should not be interpreted as representing official policies, either expressed
* or implied, of BetaSteward_at_googlemail.com.
*/

package mage.abilities;

import mage.constants.AbilityType;
import mage.constants.AsThoughEffectType;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.MageObject;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.keyword.FlashAbility;
import mage.game.Game;

import java.util.UUID;
import mage.constants.SpellAbilityType;
import mage.cards.SplitCard;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class SpellAbility extends ActivatedAbilityImpl<SpellAbility> {

    private SpellAbilityType spellAbilityType;

    public SpellAbility(ManaCost cost, String cardName) {
        this(cost, cardName, Zone.HAND);
    }

    public SpellAbility(ManaCost cost, String cardName, Zone zone) {
        this(cost, cardName, zone, SpellAbilityType.BASE);
    }

    public SpellAbility(ManaCost cost, String cardName, Zone zone, SpellAbilityType spellAbilityType) {
        super(AbilityType.SPELL, zone);
        this.spellAbilityType = spellAbilityType;
        this.addManaCost(cost);
        switch(spellAbilityType) {
            case SPLIT_FUSED:
                this.name = "Cast fused " + cardName;
                break;
            default:
                this.name = "Cast " + cardName;
        }
        
    }

//    public SpellAbility(Cost cost, String cardName, Effect effect, Zone zone) {
//        super(zone, effect, cost);
//        this.spellAbilityType = SpellAbilityType.BASE;
//        this.name = "Cast " + cardName;
//    }

    public SpellAbility(SpellAbility ability) {
        super(ability);
        this.spellAbilityType = ability.spellAbilityType;
    }

    @Override
    public boolean canActivate(UUID playerId, Game game) {
        MageObject object = game.getObject(sourceId);
        if ((object.getCardType().contains(CardType.INSTANT) ||
                object.getAbilities().containsKey(FlashAbility.getInstance().getId()) ||
                game.getContinuousEffects().asThough(sourceId, AsThoughEffectType.CAST, game) ||
                game.canPlaySorcery(playerId))) {
            if (spellAbilityType.equals(SpellAbilityType.SPLIT)) {
                return false;
            }
            // fix for Gitaxian Probe and casting opponent's spells
            if (!controllerId.equals(playerId)) {
                return false;
            }
            if (this.getManaCosts().isEmpty()) {
                return false;
            }
            if (costs.canPay(sourceId, controllerId, game)) {
                if (getSpellAbilityType().equals(SpellAbilityType.SPLIT_FUSED)) {
                    SplitCard splitCard = (SplitCard) game.getCard(getSourceId());
                    if (splitCard != null) {
                        return (splitCard.getLeftHalfCard().getSpellAbility().canChooseTarget(game) 
                                && splitCard.getRightHalfCard().getSpellAbility().canChooseTarget(game));
                    }
                    return false;

                } else {
                    return  canChooseTarget(game);
                }
            }
        }
        return false;
    }

    @Override
    public String getActivatedMessage(Game game) {
        return " casts " + getMessageText(game);
    }

    @Override
    public String getRule(boolean all) {
        if (all) {
            return super.getRule(all) + name;
        }
        return super.getRule(false);
    }

    public void clear() {
        getChoices().clearChosen();
        getTargets().clearChosen();
        this.manaCosts.clearPaid();
        this.costs.clearPaid();
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public SpellAbility copy() {
        return new SpellAbility(this);
    }

    public SpellAbility copySpell() {
        SpellAbility spell = new SpellAbility(this);
        spell.id = UUID.randomUUID();
        return spell;
    }

    public SpellAbilityType getSpellAbilityType() {
        return spellAbilityType;
    }

    public void setSpellAbilityType(SpellAbilityType spellAbilityType) {
        this.spellAbilityType = spellAbilityType;
    }

}
