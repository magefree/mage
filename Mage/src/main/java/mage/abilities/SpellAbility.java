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

import java.util.UUID;
import mage.MageObject;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.keyword.FlashAbility;
import mage.cards.SplitCard;
import mage.constants.AbilityType;
import mage.constants.AsThoughEffectType;
import mage.constants.SpellAbilityType;
import mage.constants.TimingRule;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class SpellAbility extends ActivatedAbilityImpl {

    protected SpellAbilityType spellAbilityType;
    protected String cardName;

    public SpellAbility(ManaCost cost, String cardName) {
        this(cost, cardName, Zone.HAND);
    }

    public SpellAbility(ManaCost cost, String cardName, Zone zone) {
        this(cost, cardName, zone, SpellAbilityType.BASE);
    }

    public SpellAbility(ManaCost cost, String cardName, Zone zone, SpellAbilityType spellAbilityType) {
        super(AbilityType.SPELL, zone);
        this.cardName = cardName;
        this.spellAbilityType = spellAbilityType;
        this.addManaCost(cost);
        switch (spellAbilityType) {
            case SPLIT_FUSED:
                this.name = "Cast fused " + cardName;
                break;
            default:
                this.name = "Cast " + cardName;
        }

    }

    public SpellAbility(final SpellAbility ability) {
        super(ability);
        this.spellAbilityType = ability.spellAbilityType;
        this.cardName = ability.cardName;
    }

    public boolean spellCanBeActivatedRegularlyNow(UUID playerId, Game game) {
        MageObject object = game.getObject(sourceId);
        return timing == TimingRule.INSTANT
                || object.hasAbility(FlashAbility.getInstance().getId(), game)
                || game.canPlaySorcery(playerId);
    }

    @Override
    public boolean canActivate(UUID playerId, Game game) {
        if (game.getContinuousEffects().asThough(sourceId, AsThoughEffectType.CAST_AS_INSTANT, this, playerId, game) // check this first to allow Offering in main phase
                || this.spellCanBeActivatedRegularlyNow(playerId, game)) {
            if (spellAbilityType.equals(SpellAbilityType.SPLIT)) {
                return false;
            }
            // fix for Gitaxian Probe and casting opponent's spells
            if (!game.getContinuousEffects().asThough(getSourceId(), AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, playerId, game)
                    && !controllerId.equals(playerId)) {
                return false;
            }
            // Check if rule modifying events prevent to cast the spell in check playable mode
            if (this.isCheckPlayableMode()) {
                if (game.getContinuousEffects().preventedByRuleModification(
                        GameEvent.getEvent(GameEvent.EventType.CAST_SPELL, this.getId(), this.getSourceId(), playerId), this, game, true)) {
                    return false;
                }
            }
            // Alternate spell abilities (Flashback, Overload) can't be cast with no mana to pay option
            if (getSpellAbilityType().equals(SpellAbilityType.BASE_ALTERNATE)) {
                Player player = game.getPlayer(playerId);
                if (player != null && getSourceId().equals(player.getCastSourceIdWithAlternateMana())) {
                    return false;
                }
            }
            if (costs.canPay(this, sourceId, controllerId, game)) {
                if (getSpellAbilityType().equals(SpellAbilityType.SPLIT_FUSED)) {
                    SplitCard splitCard = (SplitCard) game.getCard(getSourceId());
                    if (splitCard != null) {
                        return (splitCard.getLeftHalfCard().getSpellAbility().canChooseTarget(game)
                                && splitCard.getRightHalfCard().getSpellAbility().canChooseTarget(game));
                    }
                    return false;

                } else {
                    return canChooseTarget(game);
                }
            }
        }
        return false;
    }

    @Override
    public String getGameLogMessage(Game game) {
        return getMessageText(game);
    }

    @Override
    public String getRule(boolean all) {
        if (all) {
            return new StringBuilder(super.getRule(all)).append(name).toString();
        }
        return super.getRule(false);
    }

    public void clear() {
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

    public String getCardName() {
        return cardName;
    }

    public int getConvertedXManaCost() {
        int xMultiplier = 0;
        for (String symbolString : getManaCosts().getSymbols()) {
            int index = symbolString.indexOf("{X}");
            while (index != -1) {
                xMultiplier++;
                symbolString = symbolString.substring(index + 3);
                index = symbolString.indexOf("{X}");
            }
        }
        return getManaCostsToPay().getX() * xMultiplier;

    }
}
