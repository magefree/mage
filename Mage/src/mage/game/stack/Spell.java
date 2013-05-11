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

package mage.game.stack;

import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.MageObject;
import mage.Mana;
import mage.ObjectColor;
import mage.abilities.Abilities;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.effects.Effect;
import mage.abilities.effects.PostResolveEffect;
import mage.cards.Card;
import mage.counters.Counter;
import mage.counters.Counters;
import mage.game.Game;
import mage.game.events.ZoneChangeEvent;
import mage.players.Player;
import mage.target.Target;
import mage.watchers.Watcher;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.Constants;
import mage.cards.SplitCard;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class Spell<T extends Spell<T>> implements StackObject, Card {

    private List<Card> spellCards = new ArrayList<Card>();
    private List<SpellAbility> spellAbilities = new ArrayList<SpellAbility>();

    private Card card;
    private SpellAbility ability;
    private UUID controllerId;
    private boolean copiedSpell;
    private Zone fromZone;
    private UUID id;

    public Spell(Card card, SpellAbility ability, UUID controllerId, Zone fromZone) {
        this.card = card;
        id = ability.getId();
        this.ability = ability;
        this.ability.setControllerId(controllerId);
        if (ability.getSpellAbilityType().equals(Constants.SpellAbilityType.SPLIT_FUSED)) {
            spellCards.add(((SplitCard) card).getLeftHalfCard());
            spellAbilities.add(((SplitCard) card).getLeftHalfCard().getSpellAbility().copy());
            spellCards.add(((SplitCard) card).getRightHalfCard());
            spellAbilities.add(((SplitCard) card).getRightHalfCard().getSpellAbility().copy());
        } else {
            spellCards.add(card);
            spellAbilities.add(ability);
        }
        this.controllerId = controllerId;
        this.fromZone = fromZone;
    }

    public Spell(final Spell<T> spell) {
        this.id = spell.id;
        for (SpellAbility spellAbility: spell.spellAbilities) {
            this.spellAbilities.add(spellAbility.copy());
        }
        for (Card spellCard: spell.spellCards) {
            this.spellCards.add(spellCard.copy());
        }
        if (spell.spellAbilities.get(0).equals(spell.ability)) {
            this.ability = spellAbilities.get(0);
        } else {
            this.ability = spell.ability.copy();
        }
        if (spell.spellCards.get(0).equals(spell.card)) {
            this.card = spellCards.get(0);
        } else {
            this.card = spell.card.copy();
        }
        this.controllerId = spell.controllerId;
        this.fromZone = spell.fromZone;
        this.copiedSpell = spell.copiedSpell;
    }


    public boolean activate(Game game, boolean noMana) {
        for (SpellAbility spellAbility: spellAbilities) {
            if (!spellAbility.activate(game, noMana)) {
                return false;
            }
        }
        return true;
    }

    public String getActivatedMessage(Game game) {
        return ability.getActivatedMessage(game);
    }

    @Override
    public boolean resolve(Game game) {
        boolean result;
        if (card.getCardType().contains(CardType.INSTANT) || card.getCardType().contains(CardType.SORCERY)) {
            int index = 0;
            result = false;
            boolean legalParts = false;
            for(SpellAbility spellAbility: this.spellAbilities) {
                if (spellAbility.getTargets().stillLegal(spellAbility, game)) {
                    legalParts = true;
                    updateOptionalCosts(index);
                    result |= spellAbility.resolve(game);
                }
                index++;
            }
            if (legalParts) {
                if (!copiedSpell) {
                    for (Effect effect : ability.getEffects()) {
                        if (effect instanceof PostResolveEffect) {
                            ((PostResolveEffect) effect).postResolve(card, ability, controllerId, game);
                            return result;
                        }
                    }
                    if (!card.isCopy() && game.getState().getZone(card.getId()) == Zone.STACK) {
                        card.moveToZone(Zone.GRAVEYARD, ability.getId(), game, false);
                    }
                }
                return result;
            }
            //20091005 - 608.2b
            game.informPlayers(getName() + " has been fizzled.");
            counter(null, game);
            return false;
        } else if (card.getCardType().contains(CardType.ENCHANTMENT) && card.getSubtype().contains("Aura")) {
            if (ability.getTargets().stillLegal(ability, game)) {
                updateOptionalCosts(0);
                if (card.putOntoBattlefield(game, Zone.HAND, ability.getId(), controllerId)) {
                    return ability.resolve(game);
                }
                return false;
            }
            //20091005 - 608.2b
            game.informPlayers(getName() + " has been fizzled.");
            counter(null, game);
            return false;
        } else {
            updateOptionalCosts(0);
            result = card.putOntoBattlefield(game, Zone.HAND, ability.getId(), controllerId);
            return result;
        }
    }

    /**
     * As we have ability in the stack, we need to update optional costs in original card.
     * This information will be used later by effects, e.g. to determine whether card was kicked or not.
     * E.g. Desolation Angel
     */
    private void updateOptionalCosts(int index) {
        Ability abilityOrig = spellCards.get(index).getAbilities().get(spellAbilities.get(index).getId());
        if (abilityOrig != null) {
            for (Object object : spellAbilities.get(index).getOptionalCosts()) {
                Cost cost = (Cost) object;
                for (Cost costOrig : abilityOrig.getOptionalCosts()) {
                    if (cost.getId().equals(costOrig.getId())) {
                        if (cost.isPaid()) {
                            costOrig.setPaid();
                        } else {
                            costOrig.clearPaid();
                        }
                        break;
                    }
                }
            }
        }
    }


    /**
     * Choose new targets for the spell
     *
     * @param game
     * @param playerId Player UUID who changes the targets.
     * @return
     */
    public boolean chooseNewTargets(Game game, UUID playerId) {
        Player player = game.getPlayer(playerId);
        if (player != null) {
            for(SpellAbility spellAbility: spellAbilities) {
                for (Target target: spellAbility.getTargets()) {
                    Target newTarget = target.copy();
                    newTarget.clearChosen();
                    for (UUID targetId: target.getTargets()) {
                        MageObject object = game.getObject(targetId);
                        String name = null;
                        if (object == null) {
                            Player targetPlayer = game.getPlayer(targetId);
                            if (targetPlayer != null) name = targetPlayer.getName();
                        } else {
                            name = object.getName();
                        }
                        if (name != null && player.chooseUse(spellAbility.getEffects().get(0).getOutcome(), "Change target from " + name + "?", game)) {
                            if (!player.chooseTarget(spellAbility.getEffects().get(0).getOutcome(), newTarget, spellAbility, game))
                                newTarget.addTarget(targetId, spellAbility, game, false);
                        }
                        else {
                            newTarget.addTarget(targetId, spellAbility, game, false);
                        }
                    }
                    target.clearChosen();
                    for (UUID newTargetId: newTarget.getTargets()) {
                        target.addTarget(newTargetId, spellAbility, game, false);
                    }
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public void counter(UUID sourceId, Game game) {
        card.moveToZone(Zone.GRAVEYARD, sourceId, game, false);
    }

    @Override
    public UUID getSourceId() {
        return card.getId();
    }

    @Override
    public UUID getControllerId() {
        return this.controllerId;
    }

    @Override
    public String getName() {
        return card.getName();
    }

    @Override
    public String getImageName() {
        return card.getImageName();
    }

    @Override
    public void setName(String name) {}

    @Override
    public Rarity getRarity() {
        return card.getRarity();
    }

    @Override
    public void setRarity(Rarity rarity) {}

    @Override
    public List<CardType> getCardType() {
        return card.getCardType();
    }

    @Override
    public List<String> getSubtype() {
        return card.getSubtype();
    }

    @Override
    public boolean hasSubtype(String subtype) {
        return card.hasSubtype(subtype);
    }

    @Override
    public List<String> getSupertype() {
        return card.getSupertype();
    }


    public List<SpellAbility> getSpellAbilities() {
        return spellAbilities;
    }

    @Override
    public Abilities<Ability> getAbilities() {
        return card.getAbilities();
    }

    @Override
    public ObjectColor getColor() {
        return card.getColor();
    }

    @Override
    public ManaCosts<ManaCost> getManaCost() {
        return card.getManaCost();
    }

    @Override
    public MageInt getPower() {
        return card.getPower();
    }

    @Override
    public MageInt getToughness() {
        return card.getToughness();
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public UUID getOwnerId() {
        return card.getOwnerId();
    }

    @Override
    public void addAbility(Ability ability) {}

    @Override
    public void addWatcher(Watcher watcher) {}

    @Override
    public SpellAbility getSpellAbility() {
        return ability;
    }

    @Override
    public void setControllerId(UUID controllerId) {
        this.ability.setControllerId(controllerId);
        for (SpellAbility spellAbility: spellAbilities) {
            spellAbility.setControllerId(controllerId);
        }
        this.controllerId = controllerId;
    }

    @Override
    public void setOwnerId(UUID controllerId) {}

    @Override
    public List<String> getRules() {
        return card.getRules();
    }

    @Override
    public List<Watcher> getWatchers() {
        return card.getWatchers();
    }

    @Override
    public String getExpansionSetCode() {
        return card.getExpansionSetCode();
    }

    @Override
    public void setExpansionSetCode(String expansionSetCode) {}

    @Override
    public void setFaceDown(boolean value) {
        throw new RuntimeException("Not implemented.");
    }

    @Override
    public boolean isFaceDown() {
        return false;
    }

    @Override
    public boolean isFlipCard() {
        return false;
    }

    @Override
    public String getFlipCardName() {
        return null;
    }

    @Override
    public boolean isSplitCard() {
        return false;
    }

    @Override
    public boolean canTransform() {
        return false;
    }

    @Override
    public Card getSecondCardFace() {
        return null;
    }

    @Override
    public void setSecondCardFace(Card card) {
    }

    @Override
    public boolean isNightCard() {
        return false;
    }

    @Override
    public Spell copy() {
        return new Spell(this);
    }

    public Spell copySpell() {
        return new Spell(this.card.copy(), this.ability.copySpell(), this.controllerId, this.fromZone);
    }

    @Override
    public void adjustChoices(Ability ability, Game game) {}
    
    @Override
    public void adjustCosts(Ability ability, Game game) {}

    @Override
    public void adjustTargets(Ability ability, Game game) {}


    @Override
    public boolean moveToZone(Zone zone, UUID sourceId, Game game, boolean flag) {
        return moveToZone(zone, sourceId, game, flag, null);
    }

    @Override
    public boolean moveToZone(Zone zone, UUID sourceId, Game game, boolean flag, ArrayList<UUID> appliedEffects) {
        throw new UnsupportedOperationException("Unsupported operation");
    }

    @Override
    public boolean moveToExile(UUID exileId, String name, UUID sourceId, Game game) {
        return moveToExile(exileId, name, sourceId, game, null);
    }

    @Override
    public boolean moveToExile(UUID exileId, String name, UUID sourceId, Game game,  ArrayList<UUID> appliedEffects) {
        ZoneChangeEvent event = new ZoneChangeEvent(this.getId(), sourceId, this.getOwnerId(), Zone.STACK, Zone.EXILED, appliedEffects);
        if (!game.replaceEvent(event)) {
            game.getStack().remove(this);
            game.rememberLKI(this.getId(), event.getFromZone(), this);

            if (exileId == null) {
                game.getExile().getPermanentExile().add(this.card);
            }
            else {
                game.getExile().createZone(exileId, name).add(this.card);
            }
            game.setZone(this.card.getId(), event.getToZone());
            game.fireEvent(event);
            return event.getToZone() == Zone.EXILED;
        }
        return false;
    }

    @Override
    public boolean putOntoBattlefield(Game game, Zone fromZone, UUID sourceId, UUID controllerId) {
        throw new UnsupportedOperationException("Unsupported operation");
    }

    @Override
    public int getCardNumber() {
        return card.getCardNumber();
    }

    @Override
    public void setCardNumber(int cid) {
        card.setCardNumber(cid);
    }

    @Override
    public boolean getUsesVariousArt() {
        return card.getUsesVariousArt();
    }

    @Override
    public void setUsesVariousArt(boolean usesVariousArt) {
        card.setUsesVariousArt(usesVariousArt);
    }

    @Override
    public List<Mana> getMana() {
        return card.getMana();
    }

    @Override
    public boolean cast(Game game, Zone fromZone, SpellAbility ability, UUID controllerId) {
        throw new UnsupportedOperationException("Unsupported operation");
    }

    @Override
    public Ability getStackAbility() {
        return this.ability;
    }

    @Override
    public void assignNewId() {
        throw new UnsupportedOperationException("Unsupported operation");
    }

    @Override
    public int getZoneChangeCounter() {
        return card.getZoneChangeCounter();
    }

    @Override
    public void addInfo(String key, String value) {
        // do nothing
    }

    public void setCopiedSpell(boolean isCopied) {
        this.copiedSpell = isCopied;
    }

    public boolean isCopiedSpell() {
        return this.copiedSpell;
    }

    public Zone getFromZone() {
        return this.fromZone;
    }

    @Override
    public void setCopy(boolean isCopy) {
        setCopiedSpell(isCopy);
    }

    @Override
    public boolean isCopy() {
        return isCopiedSpell();
    }

    @Override
    public void build() {}

    @Override
    public Counters getCounters() {
        return null;
    }

    @Override
    public void addCounters(String name, int amount, Game game) {}

    @Override
    public void addCounters(String name, int amount, Game game, ArrayList<UUID> appliedEffects) {}

    @Override
    public void addCounters(Counter counter, Game game) {}

    @Override
    public void addCounters(Counter counter, Game game, ArrayList<UUID> appliedEffects) {}

    @Override
    public void removeCounters(String name, int amount, Game game) {}

    @Override
    public void removeCounters(Counter counter, Game game) {}

    public Card getCard() {
        return card;
    }
}
