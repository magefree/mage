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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.Mana;
import mage.ObjectColor;
import mage.abilities.Abilities;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.costs.AlternativeSourceCosts;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.keyword.BestowAbility;
import mage.abilities.keyword.MorphAbility;
import mage.cards.Card;
import mage.cards.SplitCard;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.SpellAbilityType;
import mage.constants.Zone;
import mage.counters.Counter;
import mage.counters.Counters;
import mage.game.Game;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentCard;
import mage.players.Player;
import mage.util.GameLog;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class Spell extends StackObjImpl implements Card {

    private final List<Card> spellCards = new ArrayList<>();
    private final List<SpellAbility> spellAbilities = new ArrayList<>();

    private final Card card;
    private final ObjectColor color;
    private final SpellAbility ability;
    private final Zone fromZone;
    private final UUID id;

    private UUID controllerId;
    private boolean copiedSpell;
    private boolean faceDown;
    private boolean countered;

    public Spell(Card card, SpellAbility ability, UUID controllerId, Zone fromZone) {
        this.card = card;
        this.color = card.getColor(null).copy();
        id = ability.getId();
        this.ability = ability;
        this.ability.setControllerId(controllerId);
        if (ability.getSpellAbilityType().equals(SpellAbilityType.SPLIT_FUSED)) {
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
        this.countered = false;
    }

    public Spell(final Spell spell) {
        this.id = spell.id;
        for (SpellAbility spellAbility : spell.spellAbilities) {
            this.spellAbilities.add(spellAbility.copy());
        }
        for (Card spellCard : spell.spellCards) {
            this.spellCards.add(spellCard.copy());
        }
        if (spell.spellAbilities.get(0).equals(spell.ability)) {
            this.ability = this.spellAbilities.get(0);
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
        this.faceDown = spell.faceDown;
        this.color = spell.color.copy();
    }

    public boolean activate(Game game, boolean noMana) {
        if (!spellAbilities.get(0).activate(game, noMana)) {
            return false;
        }
        // if there are more abilities (fused split spell) or first ability added new abilities (splice), activate the additional abilities
        boolean ignoreAbility = true;
        boolean payNoMana = noMana;
        for (SpellAbility spellAbility : spellAbilities) {
            // costs for spliced abilities were added to main spellAbility, so pay no mana for spliced abilities
            payNoMana |= spellAbility.getSpellAbilityType().equals(SpellAbilityType.SPLICE);
            if (ignoreAbility) {
                ignoreAbility = false;
            } else {
                if (!spellAbility.activate(game, payNoMana)) {
                    return false;
                }
            }
        }
        return true;
    }

    public String getActivatedMessage(Game game) {
        StringBuilder sb = new StringBuilder();
        if (isCopiedSpell()) {
            sb.append(" copies ");
        } else {
            sb.append(" casts ");
        }
        return sb.append(ability.getGameLogMessage(game)).toString();
    }

    public String getSpellCastText(Game game) {
        for (Ability spellAbility : getAbilities()) {
            if (spellAbility instanceof MorphAbility
                    && ((AlternativeSourceCosts) spellAbility).isActivated(getSpellAbility(), game)) {
                return "a card face down";
            }
        }
        return GameLog.replaceNameByColoredName(card, getSpellAbility().toString());
    }

    @Override
    public boolean resolve(Game game) {
        boolean result;
        if (this.getCardType().contains(CardType.INSTANT) || this.getCardType().contains(CardType.SORCERY)) {
            int index = 0;
            result = false;
            boolean legalParts = false;
            // check for legal parts
            for (SpellAbility spellAbility : this.spellAbilities) {
                // if muliple modes are selected, and there are modes with targets, then at least one mode has to have a legal target or
                // When resolving a fused split spell with multiple targets, treat it as you would any spell with multiple targets.
                // If all targets are illegal when the spell tries to resolve, the spell is countered and none of its effects happen.
                // If at least one target is still legal at that time, the spell resolves, but an illegal target can't perform any actions
                // or have any actions performed on it.
                legalParts |= spellAbilityHasLegalParts(spellAbility, game);
            }
            // resolve if legal parts
            if (legalParts) {
                for (SpellAbility spellAbility : this.spellAbilities) {
                    if (spellAbilityHasLegalParts(spellAbility, game)) {
                        for (UUID modeId : spellAbility.getModes().getSelectedModes()) {
                            spellAbility.getModes().setActiveMode(modeId);
                            if (spellAbility.getTargets().stillLegal(spellAbility, game)) {
                                if (!spellAbility.getSpellAbilityType().equals(SpellAbilityType.SPLICE)) {
                                    updateOptionalCosts(index);
                                }
                                result |= spellAbility.resolve(game);
                            }
                        }
                        index++;
                    }
                }
                if (game.getState().getZone(card.getMainCard().getId()) == Zone.STACK) {
                    if (isCopy() == card.isCopy()) {
                        Player player = game.getPlayer(getControllerId());
                        if (player != null) {
                            player.moveCards(card, Zone.STACK, Zone.GRAVEYARD, ability, game);
                        }
                    }
                }
                return result;
            }
            //20091005 - 608.2b
            if (!game.isSimulation()) {
                game.informPlayers(getName() + " has been fizzled.");
            }
            counter(null, game);
            return false;
        } else if (this.getCardType().contains(CardType.ENCHANTMENT) && this.getSubtype().contains("Aura")) {
            if (ability.getTargets().stillLegal(ability, game)) {
                updateOptionalCosts(0);
                boolean bestow = this.getSpellAbility() instanceof BestowAbility;
                if (bestow) {
                    // Must be removed first time, after that will be removed by continous effect
                    // Otherwise effects like evolve trigger from creature comes into play event
                    card.getCardType().remove(CardType.CREATURE);
                    card.getSubtype().add("Aura");
                }
                if (card.putOntoBattlefield(game, fromZone, ability.getSourceId(), controllerId)) {
                    if (bestow) {
                        // card will be copied during putOntoBattlefield, so the card of CardPermanent has to be changed
                        // TODO: Find a better way to prevent bestow creatures from being effected by creature affecting abilities
                        Permanent permanent = game.getPermanent(card.getId());
                        if (permanent != null && permanent instanceof PermanentCard) {
                            permanent.setSpellAbility(ability); // otherwise spell ability without bestow will be set
                            ((PermanentCard) permanent).getCard().getCardType().add(CardType.CREATURE);
                            ((PermanentCard) permanent).getCard().getSubtype().remove("Aura");
                        }
                        card.getCardType().add(CardType.CREATURE);
                        card.getSubtype().remove("Aura");
                    }
                    return ability.resolve(game);
                }
                if (bestow) {
                    card.getCardType().add(CardType.CREATURE);
                }
                return false;
            }
            // Aura has no legal target and its a bestow enchantment -> Add it to battlefield as creature
            if (this.getSpellAbility() instanceof BestowAbility) {
                updateOptionalCosts(0);
                result = card.putOntoBattlefield(game, fromZone, ability.getSourceId(), controllerId);
                return result;
            } else {
                //20091005 - 608.2b
                if (!game.isSimulation()) {
                    game.informPlayers(getName() + " has been fizzled.");
                }
                counter(null, game);
                return false;
            }
        } else {
            updateOptionalCosts(0);
            result = card.putOntoBattlefield(game, fromZone, ability.getSourceId(), controllerId, false, faceDown);
            return result;
        }
    }

    private boolean spellAbilityHasLegalParts(SpellAbility spellAbility, Game game) {
        if (spellAbility.getModes().getSelectedModes().size() > 1) {
            boolean targetedMode = false;
            boolean legalTargetedMode = false;
            for (UUID modeId : spellAbility.getModes().getSelectedModes()) {
                spellAbility.getModes().setActiveMode(modeId);
                if (spellAbility.getTargets().size() > 0) {
                    targetedMode = true;
                    if (spellAbility.getTargets().stillLegal(spellAbility, game)) {
                        legalTargetedMode = true;
                    }
                }
            }
            if (targetedMode) {
                return legalTargetedMode;
            }
            return true;
        } else {
            return spellAbility.getTargets().stillLegal(spellAbility, game);
        }
    }

    /**
     * As we have ability in the stack, we need to update optional costs in
     * original card. This information will be used later by effects, e.g. to
     * determine whether card was kicked or not. E.g. Desolation Angel
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

    @Override
    public void counter(UUID sourceId, Game game) {
        this.countered = true;
        if (!isCopiedSpell()) {
            Player player = game.getPlayer(getControllerId());
            if (player != null) {
                Ability counteringAbility = null;
                MageObject counteringObject = game.getObject(sourceId);
                if (counteringObject instanceof StackObject) {
                    counteringAbility = ((StackObject) counteringObject).getStackAbility();
                }
                player.moveCards(card, Zone.STACK, Zone.GRAVEYARD, counteringAbility, game);
            }
        }
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
    public String getIdName() {
        String idName;
        if (card != null) {
            idName = card.getId().toString().substring(0, 3);
        } else {
            idName = getId().toString().substring(0, 3);
        }
        return getName() + " [" + idName + "]";
    }

    @Override
    public String getLogName() {
        return GameLog.getColoredObjectIdName(card);
    }

    @Override
    public String getImageName() {
        return card.getImageName();
    }

    @Override
    public void setName(String name) {
    }

    @Override
    public Rarity getRarity() {
        return card.getRarity();
    }

    @Override
    public List<CardType> getCardType() {
        if (this.getSpellAbility().getSpellAbilityType().equals(SpellAbilityType.FACE_DOWN_CREATURE)) {
            List<CardType> cardTypes = new ArrayList<>();
            cardTypes.add(CardType.CREATURE);
            return cardTypes;
        }
        if (this.getSpellAbility() instanceof BestowAbility) {
            List<CardType> cardTypes = new ArrayList<>();
            cardTypes.addAll(card.getCardType());
            cardTypes.remove(CardType.CREATURE);
            return cardTypes;
        }
        return card.getCardType();
    }

    @Override
    public List<String> getSubtype() {
        if (this.getSpellAbility() instanceof BestowAbility) {
            List<String> subtypes = new ArrayList<>();
            subtypes.addAll(card.getSubtype());
            subtypes.add("Aura");
            return subtypes;
        }
        return card.getSubtype();
    }

    @Override
    public boolean hasSubtype(String subtype) {
        if (this.getSpellAbility() instanceof BestowAbility) { // workaround for Bestow (don't like it)
            List<String> subtypes = new ArrayList<>();
            subtypes.addAll(card.getSubtype());
            subtypes.add("Aura");
            if (subtypes.contains(subtype)) {
                return true;
            }
        }
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
    public Abilities<Ability> getAbilities(Game game) {
        return card.getAbilities(game);
    }

    @Override
    public boolean hasAbility(UUID abilityId, Game game) {
        return card.hasAbility(abilityId, game);
    }

    @Override
    public ObjectColor getColor(Game game) {
        return color;
    }

    @Override
    public ManaCosts<ManaCost> getManaCost() {
        return card.getManaCost();
    }

    /**
     * 202.3b When calculating the converted mana cost of an object with an {X}
     * in its mana cost, X is treated as 0 while the object is not on the stack,
     * and X is treated as the number chosen for it while the object is on the
     * stack.
     *
     * @return
     */
    @Override
    public int getConvertedManaCost() {
        int cmc = 0;
        if (faceDown) {
            return 0;
        }
        for (Ability spellAbility : spellAbilities) {
            int xMultiplier = 0;
            for (String symbolString : spellAbility.getManaCosts().getSymbols()) {
                int index = symbolString.indexOf("{X}");
                while (index != -1) {
                    xMultiplier++;
                    symbolString = symbolString.substring(index + 3);
                    index = symbolString.indexOf("{X}");
                }
            }
            if (this.getSpellAbility().getSpellAbilityType().equals(SpellAbilityType.BASE_ALTERNATE)) {
                cmc += spellAbility.getManaCostsToPay().getX() * xMultiplier;
            } else {
                cmc += spellAbility.getManaCosts().convertedManaCost() + spellAbility.getManaCostsToPay().getX() * xMultiplier;
            }
        }
        if (this.getSpellAbility().getSpellAbilityType().equals(SpellAbilityType.BASE_ALTERNATE)) {
            cmc += getCard().getManaCost().convertedManaCost();
        }
        return cmc;
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

    public void addSpellAbility(SpellAbility spellAbility) {
        spellAbilities.add(spellAbility);
    }

    public void addAbility(Ability ability) {
    }

    @Override
    public SpellAbility getSpellAbility() {
        return ability;
    }

    public void setControllerId(UUID controllerId) {
        this.ability.setControllerId(controllerId);
        for (SpellAbility spellAbility : spellAbilities) {
            spellAbility.setControllerId(controllerId);
        }
        this.controllerId = controllerId;
    }

    @Override
    public void setOwnerId(UUID controllerId) {
    }

    @Override
    public List<String> getRules() {
        return card.getRules();
    }

    @Override
    public List<String> getRules(Game game) {
        return card.getRules(game);
    }

    @Override
    public String getExpansionSetCode() {
        return card.getExpansionSetCode();
    }

    @Override
    public String getTokenSetCode() {
        return card.getTokenSetCode();
    }

    @Override
    public void setFaceDown(boolean value, Game game) {
        faceDown = value;
    }

    @Override
    public boolean turnFaceUp(Game game, UUID playerId) {
        setFaceDown(false, game);
        return true;
    }

    @Override
    public boolean turnFaceDown(Game game, UUID playerId) {
        setFaceDown(true, game);
        return true;
    }

    @Override
    public boolean isFaceDown(Game game) {
        return faceDown;
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
    public void adjustChoices(Ability ability, Game game) {
        if (card != null) {
            card.adjustChoices(ability, game);
        }
    }

    @Override
    public void adjustCosts(Ability ability, Game game) {
        if (card != null) {
            card.adjustCosts(ability, game);
        }
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        if (card != null) {
            card.adjustTargets(ability, game);
        }
    }

    @Override
    public boolean moveToZone(Zone zone, UUID sourceId, Game game, boolean flag) {
        return moveToZone(zone, sourceId, game, flag, null);
    }

    @Override
    public boolean moveToZone(Zone zone, UUID sourceId, Game game, boolean flag, ArrayList<UUID> appliedEffects) {
        // 706.10a If a copy of a spell is in a zone other than the stack, it ceases to exist.
        // If a copy of a card is in any zone other than the stack or the battlefield, it ceases to exist.
        // These are state-based actions. See rule 704.
        if (this.isCopiedSpell() && !zone.equals(Zone.STACK)) {
            return true;
        }
        Card card = game.getCard(getSourceId());
        if (card != null) {
            return card.moveToZone(zone, sourceId, game, flag, appliedEffects);
        }
        throw new UnsupportedOperationException("Unsupported operation");
    }

    @Override
    public boolean moveToExile(UUID exileId, String name, UUID sourceId, Game game) {
        return moveToExile(exileId, name, sourceId, game, null);
    }

    @Override
    public boolean moveToExile(UUID exileId, String name, UUID sourceId, Game game, ArrayList<UUID> appliedEffects) {
        ZoneChangeEvent event = new ZoneChangeEvent(this.getId(), sourceId, this.getOwnerId(), Zone.STACK, Zone.EXILED, appliedEffects);
        if (!game.replaceEvent(event)) {
            game.getStack().remove(this);
            game.rememberLKI(this.getId(), event.getFromZone(), this);

            if (!this.isCopiedSpell()) {
                if (exileId == null) {
                    game.getExile().getPermanentExile().add(this.card);
                } else {
                    game.getExile().createZone(exileId, name).add(this.card);
                }

                game.setZone(this.card.getId(), event.getToZone());
            }
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
    public boolean putOntoBattlefield(Game game, Zone fromZone, UUID sourceId, UUID controllerId, boolean tapped) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean putOntoBattlefield(Game game, Zone fromZone, UUID sourceId, UUID controllerId, boolean tapped, boolean facedown) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean putOntoBattlefield(Game game, Zone fromZone, UUID sourceId, UUID controllerId, boolean tapped, boolean facedown, ArrayList<UUID> appliedEffects) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int getCardNumber() {
        return card.getCardNumber();
    }

    @Override
    public boolean getUsesVariousArt() {
        return card.getUsesVariousArt();
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
    public void updateZoneChangeCounter(Game game) {
        throw new UnsupportedOperationException("Unsupported operation");
    }

    @Override
    public void setZoneChangeCounter(int value, Game game) {
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
    public int getZoneChangeCounter(Game game) {
        return card.getZoneChangeCounter(game);
    }

    @Override
    public void addInfo(String key, String value, Game game) {
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
    public void build() {
    }

    @Override
    public Counters getCounters(Game game) {
        return card.getCounters(game);
    }

    @Override
    public void addCounters(String name, int amount, Game game) {
        card.addCounters(name, amount, game);
    }

    @Override
    public void addCounters(String name, int amount, Game game, ArrayList<UUID> appliedEffects) {
        card.addCounters(name, amount, game, appliedEffects);
    }

    @Override
    public void addCounters(Counter counter, Game game) {
        card.addCounters(counter, game);
    }

    @Override
    public void addCounters(Counter counter, Game game, ArrayList<UUID> appliedEffects) {
        card.addCounters(counter, game, appliedEffects);
    }

    @Override
    public void removeCounters(String name, int amount, Game game) {
        card.removeCounters(name, amount, game);
    }

    @Override
    public void removeCounters(Counter counter, Game game) {
        card.removeCounters(counter, game);
    }

    public Card getCard() {
        return card;
    }

    @Override
    public Card getMainCard() {
        return card.getMainCard();
    }

    @Override
    public void setZone(Zone zone, Game game) {
        card.setZone(zone, game);
    }

    @Override
    public void setSpellAbility(SpellAbility ability) {
        throw new UnsupportedOperationException("Not supported.");
    }

    public boolean isCountered() {
        return countered;
    }

}
