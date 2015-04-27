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

import mage.constants.AbilityType;
import mage.constants.CardType;
import mage.constants.EffectType;
import mage.constants.Zone;
import mage.MageInt;
import mage.MageObject;
import mage.ObjectColor;
import mage.abilities.*;
import mage.abilities.costs.AlternativeCost;
import mage.abilities.costs.Cost;
import mage.abilities.costs.Costs;
import mage.abilities.costs.CostsImpl;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.Effects;
import mage.choices.Choice;
import mage.choices.Choices;
import mage.game.Game;
import mage.target.Target;
import mage.target.Targets;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.cards.Card;
import mage.constants.AbilityWord;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetAmount;
import mage.watchers.Watcher;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class StackAbility implements StackObject, Ability {

    private static List<CardType> emptyCardType = new ArrayList<>();
    private static List<String> emptyString = new ArrayList<>();
    private static ObjectColor emptyColor = new ObjectColor();
    private static ManaCosts<ManaCost> emptyCost = new ManaCostsImpl<>();
    private static Costs<Cost> emptyCosts = new CostsImpl<>();
    private static Abilities<Ability> emptyAbilites = new AbilitiesImpl<>();

    private final Ability ability;
    private UUID controllerId;
    private String name;
    private String expansionSetCode;

    public StackAbility(Ability ability, UUID controllerId) {
        this.ability = ability;
        this.controllerId = controllerId;
        this.name = new StringBuilder("stack ability (").append(ability.getRule()).append(")").toString();
    }

    public StackAbility(final StackAbility spell) {
        this.ability = spell.ability.copy();
        this.controllerId = spell.controllerId;
        this.name = spell.name;
        this.expansionSetCode = spell.expansionSetCode;
    }

    @Override
    public boolean isActivated() {
        return ability.isActivated();
    }
    
    @Override
    public boolean resolve(Game game) {
        if (ability.getTargets().stillLegal(ability, game)) {
            return ability.resolve(game);
        }
        if (!game.isSimulation()) {
            game.informPlayers("Ability has been fizzled: " + getRule());
        }
        counter(null, game);
        return false;
    }

    @Override
    public void reset(Game game) { }

    @Override
    public void counter(UUID sourceId, Game game) {
        //20100716 - 603.8
        if (ability instanceof StateTriggeredAbility) {
            ((StateTriggeredAbility)ability).counter(game);
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getLogName() {
        return name;
    }
    
    @Override
    public String getImageName() {
        return name;
    }

    public String getExpansionSetCode() {
        return expansionSetCode;
    }

    @Override
    public List<CardType> getCardType() {
        return emptyCardType;
    }

    @Override
    public List<String> getSubtype() {
        return emptyString;
    }

    @Override
    public boolean hasSubtype(String subtype) {
        return false;
    }

    @Override
    public List<String> getSupertype() {
        return emptyString;
    }

    @Override
    public Abilities<Ability> getAbilities() {
        return emptyAbilites;
    }

    @Override
    public boolean hasAbility(UUID abilityId, Game game) {
        return false;
    }

    @Override
    public ObjectColor getColor() {
        return emptyColor;
    }

    @Override
    public ManaCosts<ManaCost> getManaCost() {
        return emptyCost;
    }

    @Override
    public MageInt getPower() {
        return MageInt.EmptyMageInt;
    }

    @Override
    public MageInt getToughness() {
        return MageInt.EmptyMageInt;
    }

    @Override
    public Zone getZone() {
        return this.ability.getZone();
    }

    @Override
    public UUID getId() {
        return this.ability.getId();
    }

    @Override
    public UUID getSourceId() {
        return this.ability.getSourceId();
    }

    @Override
    public UUID getControllerId() {
        return this.controllerId;
    }

    @Override
    public Costs<Cost> getCosts() {
        return emptyCosts;
    }

    @Override
    public int getConvertedManaCost() {
        // Activated abilities have an "activation cost" but they don't have a characteristic related to that while on the stack. 
        // There are certain effects that interact with the cost to activate an ability (e.g., Training Grounds, Power Artifact)
        // but nothing that looks for that quality of an ability once it's on the stack. 
        return 0;
    }

    @Override
    public Effects getEffects() {
        return ability.getEffects();
    }

    @Override
    public Effects getEffects(Game game, EffectType effectType) {
        return ability.getEffects(game, effectType);
    }

    @Override
    public String getRule() {
        return ability.getRule();
    }

    @Override
    public String getRule(boolean all) {
        return ability.getRule(all);
    }

    @Override
    public String getRule(String source) {
        return ability.getRule(source);
    }

    @Override
    public void setControllerId(UUID controllerId) {
        this.controllerId = controllerId;
    }

    @Override
    public void setSourceId(UUID sourceID) {}

    @Override
    public void addCost(Cost cost) {}

    @Override
    public void addEffect(Effect effect) {}

    @Override
    public boolean activate(Game game, boolean noMana) {
        return ability.activate(game, noMana);
    }

    @Override
    public Targets getTargets() {
        return ability.getTargets();
    }

    @Override
    public void addTarget(Target target) {}

    @Override
    public UUID getFirstTarget() {
        return ability.getFirstTarget();
    }

    @Override
    public Choices getChoices() {
        return ability.getChoices();
    }

    @Override
    public void addChoice(Choice choice) {}

    @Override
    public List<AlternativeCost> getAlternativeCosts() {
        return ability.getAlternativeCosts();
    }

    @Override
    public void addAlternativeCost(AlternativeCost cost) { }

    @Override
    public ManaCosts<ManaCost> getManaCosts() {
        return ability.getManaCosts();
    }

    @Override
    public ManaCosts<ManaCost> getManaCostsToPay ( ) {
        return ability.getManaCostsToPay();
    }

    @Override
    public void addManaCost(ManaCost cost) { }

    @Override
    public AbilityType getAbilityType() {
        return ability.getAbilityType();
    }

    @Override
    public boolean isUsesStack() {
        return true;
    }

    @Override
    public StackAbility copy() {
        return new StackAbility(this);
    }

    @Override
    public void setName(String name) { 
        this.name = name;
    }

    public void setExpansionSetCode(String expansionSetCode) {
        this.expansionSetCode = expansionSetCode;
    }

    @Override
    public void adjustChoices(Ability ability, Game game) {
        Card card = game.getCard(ability.getSourceId());
        if (card != null) {
            card.adjustChoices(ability, game);
        }
    }
    
    @Override
    public void adjustCosts(Ability ability, Game game) {
        Card card = game.getCard(ability.getSourceId());
        if (card != null) {
            card.adjustCosts(ability, game);
        }
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        Card card = game.getCard(ability.getSourceId());
        if (card != null) {
            card.adjustTargets(ability, game);
        }
    }

    @Override
    public Costs<Cost> getOptionalCosts() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void addOptionalCost(Cost cost) {}

    @Override
    public boolean checkIfClause(Game game) {
        return true;
    }

    @Override
    public void newId() {
        if (!(this instanceof MageSingleton)) {
            this.ability.newId();
        }
    }

    @Override
    public void newOriginalId() {}

    @Override
    public Ability getStackAbility() {
        return ability;
    }

    @Override
    public boolean isModal() {
        return ability.isModal();
    }

    @Override
    public void addMode(Mode mode) {}

    @Override
    public Modes getModes() {
        return ability.getModes();
    }

    @Override
    public boolean canChooseTarget(Game game) {
        return ability.canChooseTarget(game);
    }

    @Override
    public boolean isInUseableZone(Game game, MageObject source, GameEvent event) {
        throw new UnsupportedOperationException("Not supported.");
    }

    @Override
    public boolean hasSourceObjectAbility(Game game, MageObject source, GameEvent event) {
        throw new UnsupportedOperationException("Not supported.");
    }

    @Override
    public void setCopy(boolean isCopy) {
    }

    @Override
    public boolean isCopy() {
        return false;
    }

    @Override
    public boolean getRuleAtTheTop() {
        return this.ability.getRuleAtTheTop();
    }

    @Override
    public void setRuleAtTheTop(boolean ruleAtTheTop) {
        this.ability.setRuleAtTheTop(ruleAtTheTop);
    }

    @Override
    public boolean getRuleVisible() {
        return this.ability.getRuleVisible();
    }

    @Override
    public void setRuleVisible(boolean ruleVisible) {
        this.ability.setRuleVisible(ruleVisible);
    }

    @Override
    public boolean getAdditionalCostsRuleVisible() {
        return this.ability.getAdditionalCostsRuleVisible();
    }

    @Override
    public void setAdditionalCostsRuleVisible(boolean ruleAdditionalCostsVisible) {
        this.ability.setAdditionalCostsRuleVisible(ruleAdditionalCostsVisible);
    }
    
    @Override
    public UUID getOriginalId() {
        return this.ability.getOriginalId();
    }

    @Override
    public void setAbilityWord(AbilityWord abilityWord) {
        throw new UnsupportedOperationException("Not supported.");
    }

    @Override
    public boolean activateAlternateOrAdditionalCosts(MageObject sourceObject, boolean noMana, Player controller, Game game) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getGameLogMessage(Game game) {
        throw new UnsupportedOperationException("Not supported."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setCostModificationActive(boolean active) {
        throw new UnsupportedOperationException("Not supported. Only neede for flashbacked spells"); 
    }

    @Override
    public boolean getWorksFaceDown() {
        return this.ability.getWorksFaceDown();
    }

    @Override
    public void setWorksFaceDown(boolean worksFaceDown) {
        this.ability.setWorksFaceDown(worksFaceDown);
    }

    @Override
    public List<Watcher> getWatchers() {
        return this.ability.getWatchers();
    }

    @Override
    public void addWatcher(Watcher watcher) {
        throw new UnsupportedOperationException("Not supported.");
    }
    
    @Override
    public List<Ability> getSubAbilities() {
        return this.ability.getSubAbilities();
    }

    @Override
    public void addSubAbility(Ability ability) {
        throw new UnsupportedOperationException("Not supported.");
    }

    @Override
    public MageObject getSourceObject(Game game) {
        throw new UnsupportedOperationException("Not supported.");
    }

    @Override
    public MageObject getSourceObjectIfItStillExists(Game game) {
        throw new UnsupportedOperationException("Not supported.");
    }

    @Override
    public int getSourceObjectZoneChangeCounter() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setSourceObject(MageObject sourceObject, Game game) {
        throw new UnsupportedOperationException("Not supported.");
    }    

    @Override
    public int getZoneChangeCounter(Game game) {
        throw new UnsupportedOperationException("Not supported.");
    }

    @Override
    public void updateZoneChangeCounter(Game game) {
        throw new UnsupportedOperationException("Not supported.");
    }

    @Override
    public void setZoneChangeCounter(int value, Game game) {
        throw new UnsupportedOperationException("Not supported.");
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
     * @param playerId - player that can/has to change the target of the ability
     * @param forceChange - does only work for targets with maximum of one targetId
     * @param onlyOneTarget - 114.6b one target must be changed to another target
     * @param filterNewTarget restriction for the new target, if null nothing is cheched
     * @return
     */
    @Override
    public boolean chooseNewTargets(Game game, UUID playerId, boolean forceChange, boolean onlyOneTarget, FilterPermanent filterNewTarget) {
        Player player = game.getPlayer(playerId);
        if (player != null) {
            StringBuilder newTargetDescription = new StringBuilder();
            // Some abilities can have more than one mode
            for (UUID modeId : ability.getModes().getSelectedModes()) {
                Mode mode = ability.getModes().get(modeId);
                for (Target target : mode.getTargets()) {
                    Target newTarget = chooseNewTarget(player, getStackAbility(), mode, target, forceChange, filterNewTarget, game);
                    // clear the old target and copy all targets from new target
                    target.clearChosen();
                    for (UUID targetId : newTarget.getTargets()) {
                        target.addTarget(targetId, newTarget.getTargetAmount(targetId), ability, game, false);                            
                    }

                }
                newTargetDescription.append(((AbilityImpl)ability).getTargetDescription(mode.getTargets(), game));
            }

            if (newTargetDescription.length() > 0 && !game.isSimulation()) {
                game.informPlayers(this.getName() + " is now " + newTargetDescription.toString());
            }
            return true;
        }
        return false;
    }

    /**
     * Handles the change of one target instance of a mode
     * 
     * @param player - player that can choose the new target
     * @param ability
     * @param mode
     * @param target
     * @param forceChange
     * @param game
     * @return 
     */
    private Target chooseNewTarget(Player player, Ability ability, Mode mode, Target target, boolean forceChange, FilterPermanent filterNewTarget, Game game) {
        Target newTarget = target.copy();
        newTarget.clearChosen();
        for (UUID targetId : target.getTargets()) {
            String targetNames = getNamesOfTargets(targetId, game);
            // change the target?
            if (targetNames != null
                    && (forceChange || player.chooseUse(mode.getEffects().get(0).getOutcome(), "Change this target: " + targetNames + "?", game))) {               
                // choose exactly one other target
                if (forceChange && target.possibleTargets(this.getSourceId(), getControllerId(), game).size() > 1) { // controller of ability must be used (e.g. TargetOpponent)
                    int iteration = 0;
                    do {
                        if (iteration > 0 && !game.isSimulation()) {
                            game.informPlayer(player, "You may only select exactly one target that must be different from the origin target!");
                        }
                        iteration++;
                        newTarget.clearChosen();
                        // TODO: Distinction between "ability controller" and "player that can change the target" - here player is used for both 
                        newTarget.chooseTarget(mode.getEffects().get(0).getOutcome(), player.getId(), ability, game); 
                        // check target restriction 
                        if (newTarget.getFirstTarget() != null && filterNewTarget != null) {
                            Permanent newTargetPermanent = game.getPermanent(newTarget.getFirstTarget());
                            if (newTargetPermanent == null || !filterNewTarget.match(newTargetPermanent, game)) {
                                game.informPlayer(player, "Target does not fullfil the target requirements (" + filterNewTarget.getMessage() +")");
                                newTarget.clearChosen();
                            }
                        }
                    } while (player.isInGame() && (targetId.equals(newTarget.getFirstTarget()) || newTarget.getTargets().size() != 1));
                // choose a new target
                } else {
                    // build a target definition with exactly one possible target to select that replaces old target
                    Target tempTarget = target.copy();
                    if (target instanceof TargetAmount) {
                        ((TargetAmount)tempTarget).setAmountDefinition(new StaticValue(target.getTargetAmount(targetId)));
                    }
                    tempTarget.setMinNumberOfTargets(1);
                    tempTarget.setMaxNumberOfTargets(1);
                    boolean again;
                    do {
                        again = false;
                        tempTarget.clearChosen();
                        if (!tempTarget.chooseTarget(mode.getEffects().get(0).getOutcome(), player.getId(), ability, game)) {
                            if (player.chooseUse(Outcome.Benefit, "No target object selected. Reset to original target?", game)) {
                                // use previous target no target was selected
                                newTarget.addTarget(targetId, target.getTargetAmount(targetId), ability, game, false);
                            } else {
                                again = true;
                            }
                        } else {
                            // if possible add the alternate Target - it may not be included in the old definition nor in the already selected targets of the new definition
                            if (newTarget.getTargets().contains(tempTarget.getFirstTarget()) || target.getTargets().contains(tempTarget.getFirstTarget())) {
                                if (player.isHuman()) {
                                    game.informPlayer(player, "This target was already selected from origin ability. You can only keep this target!");
                                    again = true;
                                } else {
                                    newTarget.addTarget(targetId, target.getTargetAmount(targetId), ability, game, false);
                                }
                            } else if (newTarget.getFirstTarget() != null && filterNewTarget != null) {
                                Permanent newTargetPermanent = game.getPermanent(newTarget.getFirstTarget());
                                if (newTargetPermanent == null || !filterNewTarget.match(newTargetPermanent, game)) {
                                    game.informPlayer(player, "This target does not fullfil the target requirements (" + filterNewTarget.getMessage() +")");
                                    again = true;
                                }
                            } else {
                                // valid target was selected, add it to the new target definition
                                newTarget.addTarget(tempTarget.getFirstTarget(), target.getTargetAmount(targetId), ability, game, false);
                            }
                        }
                    } while (again && player.isInGame());
                }            
            }
            // keep the target
            else { 
                newTarget.addTarget(targetId, target.getTargetAmount(targetId), ability, game, false);
            }
        }    
        return newTarget;
    }
    
    
    private String getNamesOfTargets(UUID targetId, Game game) {
        MageObject object = game.getObject(targetId);
        String targetNames = null;
        if (object == null) {
            Player targetPlayer = game.getPlayer(targetId);
            if (targetPlayer != null) {
                targetNames = targetPlayer.getName();
            }
        } else {
            targetNames = object.getName();
        }
        return targetNames;
    }    
    
}
