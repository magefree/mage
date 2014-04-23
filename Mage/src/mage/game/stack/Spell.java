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
import mage.abilities.Mode;
import mage.abilities.SpellAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.PostResolveEffect;
import mage.abilities.keyword.BestowAbility;
import mage.cards.Card;
import mage.cards.SplitCard;
import mage.constants.CardType;
import mage.constants.Outcome;
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
import mage.target.Target;
import mage.target.TargetAmount;
import mage.watchers.Watcher;

/**
 *
 * @author BetaSteward_at_googlemail.com
 * @param <T>
 */
public class Spell<T extends Spell<T>> implements StackObject, Card {

    private final List<Card> spellCards = new ArrayList<>();
    private final List<SpellAbility> spellAbilities = new ArrayList<>();

    private final Card card;
    private final SpellAbility ability;
    private final Zone fromZone;
    private final UUID id;

    private UUID controllerId;
    private boolean copiedSpell;

    public Spell(Card card, SpellAbility ability, UUID controllerId, Zone fromZone) {
        this.card = card;
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
    }


    public boolean activate(Game game, boolean noMana) {
        if (!spellAbilities.get(0).activate(game, noMana)) {
            return false;
        }
        // if there are more abilities (fused split spell) or first ability added new abilities (splice), activate the additional abilities
        boolean ignoreAbility = true;
        boolean payNoMana = noMana;
        for (SpellAbility spellAbility: spellAbilities) {
            // costs for spliced abilities were added to main spellAbility, so pay no man for spliced abilities
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
        return ability.getGameLogMessage(game);
    }

    @Override
    public boolean resolve(Game game) {
        boolean result;
        if (this.getCardType().contains(CardType.INSTANT) || this.getCardType().contains(CardType.SORCERY)) {
            int index = 0;
            result = false;
            boolean legalParts = false;
            // check for legal parts
            for(SpellAbility spellAbility: this.spellAbilities) {
                // if muliple modes are selected, and there are modes with targets, then at least one mode has to have a legal target or 
                // When resolving a fused split spell with multiple targets, treat it as you would any spell with multiple targets. 
                // If all targets are illegal when the spell tries to resolve, the spell is countered and none of its effects happen. 
                // If at least one target is still legal at that time, the spell resolves, but an illegal target can’t perform any actions
                // or have any actions performed on it. 
                legalParts |= spellAbilityHasLegalParts(spellAbility, game);
            }
            // resolve if legal parts
            if (legalParts) {
                for(SpellAbility spellAbility: this.spellAbilities) {
                    if (spellAbilityHasLegalParts(spellAbility, game)) {
                        for (UUID modeId :spellAbility.getModes().getSelectedModes()) {
                            spellAbility.getModes().setMode(spellAbility.getModes().get(modeId));
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
        } else if (this.getCardType().contains(CardType.ENCHANTMENT) && this.getSubtype().contains("Aura")) {
            if (ability.getTargets().stillLegal(ability, game)) {
                updateOptionalCosts(0);
                boolean bestow = this.getSpellAbility() instanceof BestowAbility;
                if (bestow) { 
                    // Must be removed first time, after that will be removed by continous effect
                    // Otherwise effects like evolve trigger from creature comes into play event
                    card.getCardType().remove(CardType.CREATURE);
                }
                if (card.putOntoBattlefield(game, fromZone, ability.getId(), controllerId)) {
                    if (bestow) { 
                        // card will be copied during putOntoBattlefield, so the card of CardPermanent has to be changed
                        // TODO: Find a better way to prevent bestow creatures from being effected by creature affecting abilities
                        Permanent permanent = game.getPermanent(card.getId());
                        if (permanent != null && permanent instanceof PermanentCard) {
                                ((PermanentCard) permanent).getCard().getCardType().add(CardType.CREATURE);
                        }
                        card.getCardType().add(CardType.CREATURE);
                    }                
                    game.getState().handleSimultaneousEvent(game);
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
                result = card.putOntoBattlefield(game, fromZone, ability.getId(), controllerId);
                game.getState().handleSimultaneousEvent(game);
                return result;
            } else {
                //20091005 - 608.2b
                game.informPlayers(getName() + " has been fizzled.");
                counter(null, game);
                return false;
            }
        } else {
            updateOptionalCosts(0);
            result = card.putOntoBattlefield(game, fromZone, ability.getId(), controllerId);
            game.getState().handleSimultaneousEvent(game);
            return result;
        }
    }

    private boolean spellAbilityHasLegalParts(SpellAbility spellAbility, Game game) {
        if (spellAbility.getModes().getSelectedModes().size() > 1) {                    
            boolean targetedMode = false;
            boolean legalTargetedMode = false;
            for (UUID modeId :spellAbility.getModes().getSelectedModes()) {
                spellAbility.getModes().setMode(spellAbility.getModes().get(modeId));
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
        return chooseNewTargets(game, playerId, false, false);
    }

    /**
     * 114.6. Some effects allow a player to change the target(s) of a spell or
     * ability, and other effects allow a player to choose new targets for a
     * spell or ability. 
     * 
     * 114.6a If an effect allows a player to "change the
     * target(s)" of a spell or ability, each target can be changed only to
     * another legal target. If a target can't be changed to another legal
     * target, the original target is unchanged, even if the original target is
     * itself illegal by then. If all the targets aren't changed to other legal
     * targets, none of them are changed. 
     * 
     * 114.6b If an effect allows a player to "change a target" of a 
     * spell or ability, the process described in rule 114.6a
     * is followed, except that only one of those targets may be changed
     * (rather than all of them or none of them). 
     * 
     * 114.6c If an effect allows a
     * player to "change any targets" of a spell or ability, the process
     * described in rule 114.6a is followed, except that any number of those
     * targets may be changed (rather than all of them or none of them). 
     * 
     * 114.6d If an effect allows a player to "choose new targets" for a spell or
     * ability, the player may leave any number of the targets unchanged, even
     * if those targets would be illegal. If the player chooses to change some
     * or all of the targets, the new targets must be legal and must not cause
     * any unchanged targets to become illegal. 
     * 
     * 114.6e When changing targets or
     * choosing new targets for a spell or ability, only the final set of
     * targets is evaluated to determine whether the change is legal.
     *
     * Example: Arc Trail is a sorcery that reads "Arc Trail deals 2 damage to
     * target creature or player and 1 damage to another target creature or
     * player." The current targets of Arc Trail are Runeclaw Bear and Llanowar
     * Elves, in that order. You cast Redirect, an instant that reads "You may
     * choose new targets for target spell," targeting Arc Trail. You can change
     * the first target to Llanowar Elves and change the second target to
     * Runeclaw Bear.
     *
     * 114.7. Modal spells and abilities may have different targeting
     * requirements for each mode. An effect that allows a player to change the
     * target(s) of a modal spell or ability, or to choose new targets for a
     * modal spell or ability, doesn't allow that player to change its mode.
     * (See rule 700.2.)
     *
     * 706.10c Some effects copy a spell or ability and state that its
     * controller may choose new targets for the copy. The player may leave any
     * number of the targets unchanged, even if those targets would be illegal.
     * If the player chooses to change some or all of the targets, the new
     * targets must be legal. Once the player has decided what the copy's
     * targets will be, the copy is put onto the stack with those targets.
     *
     * @param game
     * @param playerId
     * @param forceChange - does only work for targets with maximum of one
     * targetId
     * @param onlyOneTarget - 114.6b one target must be changed to another
     * target
     * @return
     */
    public boolean chooseNewTargets(Game game, UUID playerId, boolean forceChange, boolean onlyOneTarget) {
        Player player = game.getPlayer(playerId);
        if (player != null) {
            // Fused split spells or spells where "Splice on Arcane" was used can have more than one ability
            for (SpellAbility spellAbility : spellAbilities) {
                // Some spells can have more than one mode
                for (UUID modeId : spellAbility.getModes().getSelectedModes()) {
                    Mode mode = spellAbility.getModes().get(modeId);
                    for (Target target : mode.getTargets()) {
                        Target newTarget = chooseNewTarget(player, spellAbility, mode, target, forceChange, game);
                        // clear the old target and copy all targets from new target
                        target.clearChosen();
                        for (UUID targetId : newTarget.getTargets()) {
                            target.addTarget(targetId, newTarget.getTargetAmount(targetId), spellAbility, game, false);                            
                        }

                    }
                }

            }
            return true;
        }
        return false;
    }

    /**
     * Handles the change of one target instance of a mode
     * 
     * @param player
     * @param spellAbility
     * @param mode
     * @param target
     * @param forceChange
     * @param game
     * @return 
     */
    private Target chooseNewTarget(Player player, SpellAbility spellAbility, Mode mode, Target target, boolean forceChange, Game game) {
        Target newTarget = target.copy();
        newTarget.clearChosen();
        for (UUID targetId : target.getTargets()) {
            String targetNames = getNamesOftargets(targetId, game);
            // change the target?
            if (targetNames != null
                    && (forceChange || player.chooseUse(mode.getEffects().get(0).getOutcome(), "Change this target: " + targetNames + "?", game))) {               
                // choose exactly one other target
                if (forceChange && target.possibleTargets(this.getSourceId(), player.getId(), game).size() > 1) {
                    int iteration = 0;
                    do {
                        if (iteration > 0) {
                            game.informPlayer(player, "You may only select exactly one target that must be different from the origin target!");
                        }
                        iteration++;
                        newTarget.clearChosen();
                        newTarget.chooseTarget(mode.getEffects().get(0).getOutcome(), player.getId(), spellAbility, game);
                    } while (player.isInGame() && (targetId.equals(newTarget.getFirstTarget()) || newTarget.getTargets().size() != 1));
                // choose a new target
                } else {
                    // build a target definition with exactly one possible target to select that replaces old target
                    Target tempTarget = target.copy();
                    if (target instanceof TargetAmount) {
                        ((TargetAmount)tempTarget).setAmountDefinition(new StaticValue(target.getTargetAmount(targetId)));
                    }
                    tempTarget.setMinNumberOfTargets(1);
                    tempTarget.setMaxNumberOftargets(1);
                    boolean again;
                    do {
                        again = false;
                        tempTarget.clearChosen();
                        if (!tempTarget.chooseTarget(mode.getEffects().get(0).getOutcome(), player.getId(), spellAbility, game)) {
                            if (player.chooseUse(Outcome.Benefit, "No target object selected. Reset to original target?", game)) {
                                // use previous target no target was selected
                                newTarget.addTarget(targetId, target.getTargetAmount(targetId), spellAbility, game, false);
                            } else {
                                again = true;
                            }
                        } else {
                            // if possible add the alternate Target - it may not be included in the old definition nor in the already selected targets of the new definition
                            if (newTarget.getTargets().contains(tempTarget.getFirstTarget()) || target.getTargets().contains(tempTarget.getFirstTarget())) {
                                if(player.isHuman()) {
                                    game.informPlayer(player, "This target was already selected from origin spell. You can only keep this target!");
                                    again = true;
                                } else {
                                    newTarget.addTarget(targetId, target.getTargetAmount(targetId), spellAbility, game, false);
                                }
                            } else {
                                // valid target was selected, add it to the new target definition
                                newTarget.addTarget(tempTarget.getFirstTarget(), target.getTargetAmount(targetId), spellAbility, game, false);
                            }
                        }
                    } while (again && player.isInGame());
                }            
            }
            // keep the target
            else { 
                newTarget.addTarget(targetId, target.getTargetAmount(targetId), spellAbility, game, false);
            }
        }    
        return newTarget;
    }
    
    
    private String getNamesOftargets(UUID targetId, Game game) {
        MageObject object = game.getObject(targetId);
        String name = null;
        if (object == null) {
            Player targetPlayer = game.getPlayer(targetId);
            if (targetPlayer != null) {
                name = targetPlayer.getName();
            }
        } else {
            name = object.getName();
        }
        return name;
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

    public void addSpellAbility(SpellAbility spellAbility) {
        spellAbilities.add(spellAbility);
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
    public String getTokenSetCode() {
        return card.getTokenSetCode();
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
    public void setFlipCard(boolean flipCard) {
        throw new UnsupportedOperationException("Not supported."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setFlipCardName(String flipCardName) {
        throw new UnsupportedOperationException("Not supported."); //To change body of generated methods, choose Tools | Templates.
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
        // 706.10a If a copy of a spell is in a zone other than the stack, it ceases to exist.
        // If a copy of a card is in any zone other than the stack or the battlefield, it ceases to exist.
        // These are state-based actions. See rule 704.
        if (this.isCopiedSpell() && !zone.equals(Zone.STACK)) {
           return true;
        }
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
    public boolean putOntoBattlefield(Game game, Zone fromZone, UUID sourceId, UUID controllerId, boolean tapped) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean putOntoBattlefield(Game game, Zone fromZone, UUID sourceId, UUID controllerId, boolean tapped, ArrayList<UUID> appliedEffects) {
        throw new UnsupportedOperationException("Not supported yet.");
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
