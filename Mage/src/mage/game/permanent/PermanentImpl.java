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

package mage.game.permanent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import mage.MageObject;
import mage.MageObjectReference;
import mage.abilities.Abilities;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.HexproofAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.keyword.InfectAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.MorphAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.abilities.keyword.ShroudAbility;
import mage.abilities.keyword.WitherAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.AsThoughEffectType;
import mage.constants.CardType;
import mage.constants.EffectType;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.counters.Counter;
import mage.counters.CounterType;
import mage.counters.Counters;
import mage.game.Game;
import mage.game.command.CommandObject;
import mage.game.events.DamageCreatureEvent;
import mage.game.events.DamagePlaneswalkerEvent;
import mage.game.events.DamagedCreatureEvent;
import mage.game.events.DamagedPlaneswalkerEvent;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.players.Player;

/**
 * @author BetaSteward_at_googlemail.com
 */
public abstract class PermanentImpl extends CardImpl implements Permanent {

    protected boolean tapped;
    protected boolean flipped;
    protected boolean transformed;
    protected boolean monstrous;
    protected boolean manifested = false;
    protected boolean morphed = false;
    protected UUID originalControllerId;
    protected UUID controllerId;
    protected UUID beforeResetControllerId;
    protected int damage;
    protected boolean controlledFromStartOfControllerTurn;
    protected int turnsOnBattlefield;
    protected boolean phasedIn = true;
    protected boolean faceDown;
    protected boolean attacking;
    protected int blocking;
    // number of creatures the permanent can block
    protected int maxBlocks = 1;
    // minimal number of creatures the creature can be blocked by
    protected int minBlockedBy = 1;
    // maximal number of creatures the creature can be blocked by  0 = no restriction
    protected int maxBlockedBy = 0;
    protected boolean removedFromCombat;
    protected boolean deathtouched;
    protected List<UUID> attachments = new ArrayList<>();
    protected Map<String, List<UUID>> connectedCards = new HashMap<>();
    protected HashSet<MageObjectReference> dealtDamageByThisTurn;
    protected UUID attachedTo;
    protected UUID pairedCard;
    protected Counters counters;
    protected List<Counter> markedDamage;
    protected int timesLoyaltyUsed = 0;
    protected Map<String, String> info;
    
    private static final List<UUID> emptyList = Collections.unmodifiableList(new ArrayList<UUID>());

    public PermanentImpl(UUID ownerId, UUID controllerId, String name) {
        super(ownerId, name);
        this.originalControllerId = controllerId;
        this.controllerId = controllerId;
        this.counters = new Counters();
    }

    public PermanentImpl(UUID id, UUID ownerId, UUID controllerId, String name) {
        super(id, ownerId, name);
        this.originalControllerId = controllerId;
        this.controllerId = controllerId;
        this.counters = new Counters();
    }

    public PermanentImpl(final PermanentImpl permanent) {
        super(permanent);
        this.tapped = permanent.tapped;
        this.flipped = permanent.flipped;
        this.originalControllerId = permanent.originalControllerId;
        this.controllerId = permanent.controllerId;
        this.damage = permanent.damage;
        this.controlledFromStartOfControllerTurn = permanent.controlledFromStartOfControllerTurn;
        this.turnsOnBattlefield = permanent.turnsOnBattlefield;
        this.phasedIn = permanent.phasedIn;
        this.faceDown = permanent.faceDown;
        this.attacking = permanent.attacking;
        this.blocking = permanent.blocking;
        this.maxBlocks = permanent.maxBlocks;
        this.deathtouched = permanent.deathtouched;
        this.attachments.addAll(permanent.attachments);
        for (Map.Entry<String, List<UUID>> entry: permanent.connectedCards.entrySet()) {
            this.connectedCards.put(entry.getKey(), entry.getValue());
        }
        if (permanent.dealtDamageByThisTurn != null) {
            dealtDamageByThisTurn = new HashSet<>(permanent.dealtDamageByThisTurn);
        }
        if (permanent.markedDamage != null) {
            markedDamage = new ArrayList<>();
            for (Counter counter : permanent.markedDamage) {
                markedDamage.add(counter.copy());
            }
        }
        if (permanent.info != null) {
            info = new HashMap<>();
            info.putAll(permanent.info);
        }
        this.counters = permanent.counters.copy();
        this.attachedTo = permanent.attachedTo;
        this.minBlockedBy = permanent.minBlockedBy;
        this.maxBlockedBy = permanent.maxBlockedBy;
        this.transformed = permanent.transformed;
        this.monstrous = permanent.monstrous;
        this.pairedCard = permanent.pairedCard;
        this.timesLoyaltyUsed = permanent.timesLoyaltyUsed;

        this.morphed = permanent.morphed;
        this.manifested = permanent.manifested;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(this.name);
        sb.append("-").append(this.expansionSetCode);
        if (copy) {
            sb.append(" [Copy]");
        }
        return sb.toString();
    }

    @Override
    public void setControllerId(UUID controllerId) {
        this.controllerId = controllerId;
        abilities.setControllerId(controllerId);
    }

    /**
     * Called before each applyEffects or if after a permanent was copied for the copied object
     *
     * @param game
     */
    @Override
    public void reset(Game game) {
        this.resetControl();
        this.maxBlocks = 1;
        this.minBlockedBy = 1;
        this.maxBlockedBy = 0;
        this.copy = false;
    }

    @Override
    public String getValue() {
        StringBuilder sb = new StringBuilder(1024);
        sb.append(controllerId).append(name).append(tapped).append(damage);
        sb.append(subtype).append(supertype).append(power.getValue()).append(toughness.getValue());
        sb.append(abilities.getValue());
        for (Counter counter : getCounters().values()) {
            sb.append(counter.getName()).append(counter.getCount());
        }
        return sb.toString();
    }

    @Override
    public void addInfo(String key, String value, Game game) {
        if (info == null) {
            info = new HashMap<>();
        }
        if (value == null || value.isEmpty()) {
            info.remove(key);
        } else {
            info.put(key, value);
        }
    }

    @Override
    public List<String> getRules(Game game) {
        try {
            List<String> rules = getRules();
            if (info != null) {
                for (String data : info.values()) {
                    rules.add(data);
                }
            }
            return rules;
        } catch (Exception e) {
            return rulesError;
        }
    }

    @Override
    public Abilities<Ability> getAbilities() {
        return abilities;
    }

    @Override
    public Abilities<Ability> getAbilities(Game game) {
        return abilities;
    }
    
    @Override
    @Deprecated
    public void addAbility(Ability ability) {
        throw new UnsupportedOperationException("Unsupported operation: use addAbility(Ability ability, Game game) instead");
    }

    @Override
    public void addAbility(Ability ability, Game game) {
        if (!abilities.containsKey(ability.getId())) {
            Ability copyAbility = ability.copy();
            copyAbility.setControllerId(controllerId);
            copyAbility.setSourceId(objectId);
            game.getState().addAbility(copyAbility, this);
            abilities.add(copyAbility);
        }
    }
    @Override
    public void addAbility(Ability ability, UUID sourceId, Game game) {
        addAbility(ability, sourceId, game, true);
    }

    @Override
    public void addAbility(Ability ability, UUID sourceId, Game game, boolean createNewId) {
        if (!abilities.containsKey(ability.getId())) {
            Ability copyAbility = ability.copy();
            if (createNewId) {
                copyAbility.newId(); // needed so that source can get an ability multiple times (e.g. Raging Ravine)
            }
            copyAbility.setControllerId(controllerId);
            copyAbility.setSourceId(objectId);
            game.getState().addAbility(copyAbility, sourceId, this);
            abilities.add(copyAbility);
        } else if (!createNewId) {
            // triggered abilities must be added to the state().triggerdAbilities
            // still as long as the prev. permanent is known to the LKI (e.g. Showstopper) so gained dies triggered ability will trigger
            if (!game.getBattlefield().containsPermanent(this.getId())) {
                Ability copyAbility = ability.copy();
                copyAbility.setControllerId(controllerId);
                copyAbility.setSourceId(objectId);
                game.getState().addAbility(copyAbility, sourceId, this);
            }
        }
    }

    @Override
    public void removeAllAbilities(UUID sourceId, Game game) {
        getAbilities().clear();
    }

    @Override
    public Counters getCounters() {
        return counters;
    }

    @Override
    public void addCounters(String name, int amount, Game game) {
        addCounters(name, amount, game, null);
    }

    @Override
    public void addCounters(String name, int amount, Game game, ArrayList<UUID> appliedEffects) {
        GameEvent countersEvent = GameEvent.getEvent(GameEvent.EventType.ADD_COUNTERS, objectId, controllerId, name, amount);
        countersEvent.setAppliedEffects(appliedEffects);
        if (!game.replaceEvent(countersEvent)) {
            for (int i = 0; i < countersEvent.getAmount(); i++) {
                GameEvent event = GameEvent.getEvent(GameEvent.EventType.ADD_COUNTER, objectId, controllerId, name, 1);
                event.setAppliedEffects(appliedEffects);
                if (!game.replaceEvent(event)) {
                    counters.addCounter(name, 1);
                    game.fireEvent(GameEvent.getEvent(GameEvent.EventType.COUNTER_ADDED, objectId, controllerId, name, 1));
                }
            }
            game.fireEvent(GameEvent.getEvent(GameEvent.EventType.COUNTERS_ADDED, objectId, controllerId, name, amount));
        }
    }

    @Override
    public void addCounters(Counter counter, Game game, ArrayList<UUID> appliedEffects) {
        GameEvent countersEvent = GameEvent.getEvent(GameEvent.EventType.ADD_COUNTERS, objectId, controllerId, counter.getName(), counter.getCount());
        countersEvent.setAppliedEffects(appliedEffects);
        if (!game.replaceEvent(countersEvent)) {
            int amount = countersEvent.getAmount();
            for (int i = 0; i < amount; i++) {
                Counter eventCounter = counter.copy();
                eventCounter.remove(eventCounter.getCount() - 1);
                GameEvent event = GameEvent.getEvent(GameEvent.EventType.ADD_COUNTER, objectId, controllerId, counter.getName(), 1);
                event.setAppliedEffects(appliedEffects);
                if (!game.replaceEvent(event)) {
                    counters.addCounter(eventCounter);
                    game.fireEvent(GameEvent.getEvent(GameEvent.EventType.COUNTER_ADDED, objectId, controllerId, counter.getName(), 1));
                }
            }
            game.fireEvent(GameEvent.getEvent(GameEvent.EventType.COUNTERS_ADDED, objectId, controllerId, counter.getName(), amount));
        }
    }

    @Override
    public void removeCounters(String name, int amount, Game game) {
        for (int i = 0; i < amount; i++) {
            counters.removeCounter(name, 1);
            GameEvent event = GameEvent.getEvent(GameEvent.EventType.COUNTER_REMOVED, objectId, controllerId);
            event.setData(name);
            game.fireEvent(event);
        }
    }

    @Override
    public void removeCounters(Counter counter, Game game) {
        removeCounters(counter.getName(), counter.getCount(), game);
    }

    @Override
    public int getTurnsOnBattlefield() {
        return turnsOnBattlefield;
    }

    @Override
    public void beginningOfTurn(Game game) {
        if (game.getActivePlayerId().equals(this.controllerId)) {
            this.controlledFromStartOfControllerTurn = true;
        }
    }

    @Override
    public void endOfTurn(Game game) {
        this.damage = 0;
        this.timesLoyaltyUsed = 0;
        this.turnsOnBattlefield++;
        this.deathtouched = false;
        dealtDamageByThisTurn = null;
        for (Ability ability : this.abilities) {
            ability.reset(game);
        }
    }

    @Override
    public void addLoyaltyUsed() {
        this.timesLoyaltyUsed++;
    }

    @Override
    public boolean canLoyaltyBeUsed(Game game) {
        Player controller = game.getPlayer(controllerId);
        if (controller != null) {
            return controller.getLoyaltyUsePerTurn() > timesLoyaltyUsed;
        }
        return false;
    }


    @Override
    public boolean isTapped() {
        return tapped;
    }

    @Override
    public void setTapped(boolean tapped) {
        this.tapped = tapped;
    }

    @Override
    public boolean canTap() {
        return !cardType.contains(CardType.CREATURE) || !hasSummoningSickness();
    }

    @Override
    public boolean untap(Game game) {
        //20091005 - 701.15b
        if (tapped) {
            if (!replaceEvent(EventType.UNTAP, game)) {
                this.tapped = false;
                fireEvent(EventType.UNTAPPED, game);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean tap(Game game) {
        //20091005 - 701.15a
        if (!tapped) {
            if (!replaceEvent(EventType.TAP, game)) {
                this.tapped = true;
                fireEvent(EventType.TAPPED, game);
                return true;
            }
        }
        return false;
    }

    @Override
    public void setFaceDown(boolean value, Game game) {
        this.faceDown = value;
    }

    @Override
    public boolean isFaceDown(Game game) {
        return faceDown;
    }

    @Override
    public boolean isFlipped() {
        return flipped;
    }

    @Override
    public boolean unflip(Game game) {
        if (flipped) {
            if (!replaceEvent(EventType.UNFLIP, game)) {
                this.flipped = false;
                fireEvent(EventType.UNFLIPPED, game);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean flip(Game game) {
        if (!flipped) {
            if (!replaceEvent(EventType.FLIP, game)) {
                this.flipped = true;
                fireEvent(EventType.FLIPPED, game);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean transform(Game game) {
        if (canTransform) {
            if (!replaceEvent(EventType.TRANSFORM, game)) {
                setTransformed(!transformed);
                fireEvent(EventType.TRANSFORMED, game);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isPhasedIn() {
        return phasedIn;
    }

    @Override
    public boolean phaseIn(Game game) {
        if (!phasedIn) {
            if (!replaceEvent(EventType.PHASE_IN, game)) {
                this.phasedIn = true;
                if (!game.isSimulation())
                    game.informPlayers(getLogName() + " phased in");
                fireEvent(EventType.PHASED_IN, game);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean phaseOut(Game game) {
        if (phasedIn) {
            if (!replaceEvent(EventType.PHASE_OUT, game)) {
                this.phasedIn = false;
                if (!game.isSimulation())
                    game.informPlayers(getLogName() + " phased out");
                fireEvent(EventType.PHASED_OUT, game);
                return true;
            }
        }
        return false;
    }

    public void removeSummoningSickness() {
        this.controlledFromStartOfControllerTurn = true;
    }

    @Override
    public boolean wasControlledFromStartOfControllerTurn() {
        return this.controlledFromStartOfControllerTurn;
    }

    @Override
    public boolean hasSummoningSickness() {
        return !(this.controlledFromStartOfControllerTurn || this.abilities.containsKey(HasteAbility.getInstance().getId()));
    }

    @Override
    public boolean isAttacking() {
        return attacking;
    }

    @Override
    public int getBlocking() {
        return blocking;
    }

    @Override
    public int getMaxBlocks() {
        return maxBlocks;
    }

    @Override
    public int getMinBlockedBy() {
        return minBlockedBy;
    }

   @Override
    public int getMaxBlockedBy() {
        return maxBlockedBy;
    }

    @Override
    public boolean isRemovedFromCombat() {
        return removedFromCombat;
    }

    @Override
    public UUID getControllerId() {
        return this.controllerId;
    }

    @Override
    public void resetControl() {
        this.beforeResetControllerId = this.controllerId;
        this.controllerId = this.originalControllerId;
    }

    @Override
    public boolean changeControllerId(UUID controllerId, Game game) {
        Player newController = game.getPlayer(controllerId);
        if (newController != null && (!newController.hasLeft() || !newController.hasLost())) {
            this.controllerId = controllerId;
            return true;
        }
        return false;
    }

    @Override
    public boolean checkControlChanged(Game game) {
        if (!controllerId.equals(beforeResetControllerId)) {
            this.removeFromCombat(game);
            this.controlledFromStartOfControllerTurn = false;

            this.abilities.setControllerId(controllerId);
            game.getContinuousEffects().setController(objectId, controllerId);

            game.fireEvent(new GameEvent(EventType.LOST_CONTROL, objectId, objectId, beforeResetControllerId));
            game.fireEvent(new GameEvent(EventType.GAINED_CONTROL, objectId, objectId, controllerId));

            return true;
        }
        return false;
    }

    @Override
    public List<UUID> getAttachments() {
        return attachments;
    }

    @Override
    public boolean addAttachment(UUID permanentId, Game game) {
        if (!this.attachments.contains(permanentId)) {
            if (!game.replaceEvent(new GameEvent(GameEvent.EventType.ATTACH, objectId, permanentId, controllerId))) {
                this.attachments.add(permanentId);
                Permanent attachment = game.getPermanent(permanentId);
                if (attachment != null) {
                    attachment.attachTo(objectId, game);
                    game.fireEvent(new GameEvent(GameEvent.EventType.ATTACHED, objectId, permanentId, controllerId));
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean removeAttachment(UUID permanentId, Game game) {
        if (this.attachments.contains(permanentId)) {
            if (!game.replaceEvent(new GameEvent(GameEvent.EventType.UNATTACH, objectId, permanentId, controllerId))) {
                this.attachments.remove(permanentId);
                Permanent attachment = game.getPermanent(permanentId);
                if (attachment != null) {
                    attachment.attachTo(null, game);
                }
                game.fireEvent(new GameEvent(GameEvent.EventType.UNATTACHED, objectId, permanentId, controllerId));
                return true;
            }
        }
        return false;
    }

    @Override
    public UUID getAttachedTo() {
        return attachedTo;
    }

    @Override
    public void addConnectedCard(String key, UUID connectedCard) {
        if (this.connectedCards.containsKey(key)) {
            this.connectedCards.get(key).add(connectedCard);
        } else {
            List<UUID> list = new ArrayList<>();
            list.add(connectedCard);
            this.connectedCards.put(key, list);
        }
    }

    @Override
    public List<UUID> getConnectedCards(String key) {
        if (this.connectedCards.containsKey(key)) {
            return this.connectedCards.get(key);
        } else {
            return emptyList;
        }
    }

    @Override
    public void clearConnectedCards(String key) {
        if (this.connectedCards.containsKey(key)) {
            this.connectedCards.get(key).clear();
        }
    }

    @Override
    public void attachTo(UUID permanentId, Game game) {
        if (this.attachedTo != null && this.attachedTo!= permanentId) {
            Permanent attachment = game.getPermanent(this.attachedTo);
            if (attachment != null) {
                attachment.removeAttachment(this.objectId, game);
            }
        }
        this.attachedTo = permanentId;
        for (Ability ability : this.getAbilities()) {
            for (Iterator<Effect> ite = ability.getEffects(game, EffectType.CONTINUOUS).iterator(); ite.hasNext();) {
                ContinuousEffect effect = (ContinuousEffect) ite.next();
                game.getContinuousEffects().setOrder(effect);
                // It's important is to update timestamp of the copied effect in ContinuousEffects because it does the action
                for (ContinuousEffect conEffect: game.getContinuousEffects().getLayeredEffects(game)) {
                    if (conEffect.getId().equals(effect.getId())) {
                        game.getContinuousEffects().setOrder(conEffect);
                    }
                }
            }
        }
    }

    @Override
    public boolean isDeathtouched() {
        return deathtouched;
    }

    @Override
    public int getDamage() {
        return this.damage;
    }

    @Override
    public int damage(int damage, UUID sourceId, Game game, boolean combat, boolean preventable) {
        return damage(damage, sourceId, game, preventable, combat, false, null);
    }

    @Override
    public int damage(int damage, UUID sourceId, Game game, boolean combat, boolean preventable, ArrayList<UUID> appliedEffects) {
        return damage(damage, sourceId, game, preventable, combat, false, appliedEffects);
    }
    /**
     * @param damageAmount
     * @param sourceId
     * @param game
     * @param preventable
     * @param combat
     * @param markDamage   If true, damage will be dealt later in applyDamage method
     * @return
     */
    private int damage(int damageAmount, UUID sourceId, Game game, boolean preventable, boolean combat, boolean markDamage, ArrayList<UUID> appliedEffects) {
        int damageDone = 0;
        if (damageAmount > 0 && canDamage(game.getObject(sourceId), game)) {
            if (cardType.contains(CardType.PLANESWALKER)) {
                damageDone = damagePlaneswalker(damageAmount, sourceId, game, preventable, combat, markDamage, appliedEffects);
            } else {
                damageDone = damageCreature(damageAmount, sourceId, game, preventable, combat, markDamage, appliedEffects);
            }
            if (damageDone > 0) {
                UUID sourceControllerId = null;
                Abilities sourceAbilities = null;
                MageObject source = game.getPermanentOrLKIBattlefield(sourceId);
                if (source == null) {
                    StackObject stackObject = game.getStack().getStackObject(sourceId);
                    if (stackObject != null) {
                        source = stackObject.getStackAbility().getSourceObject(game);
                    } else {
                        source = game.getObject(sourceId);
                    }
                    if (source instanceof Spell) {
                        sourceAbilities = ((Spell) source).getAbilities(game);
                        sourceControllerId = ((Spell) source).getControllerId();
                    } else if (source instanceof Card) {
                        sourceAbilities = ((Card) source).getAbilities(game);
                        sourceControllerId = ((Card) source).getOwnerId();                        
                    } else if (source instanceof CommandObject){
                        sourceControllerId = ((CommandObject) source).getControllerId();
                        sourceAbilities = ((CommandObject) source).getAbilities();
                    } else {
                        source = null;
                    }
                } else {
                    sourceAbilities = ((Permanent) source).getAbilities(game);
                    sourceControllerId = ((Permanent) source).getControllerId();
                }
                if (source != null && sourceAbilities!= null) {
                    if (sourceAbilities.containsKey(LifelinkAbility.getInstance().getId())) {
                        Player player = game.getPlayer(sourceControllerId);
                        player.gainLife(damageAmount, game);
                    }
                    if (sourceAbilities.containsKey(DeathtouchAbility.getInstance().getId())) {
                        deathtouched = true;
                    }
                    if (dealtDamageByThisTurn == null) {
                        dealtDamageByThisTurn = new HashSet<>();
                    }
                    dealtDamageByThisTurn.add(new MageObjectReference(source, game));
                }
            }
        }
        return damageDone;
    }

    @Override
    public int markDamage(int damageAmount, UUID sourceId, Game game, boolean preventable, boolean combat) {
        return damage(damageAmount, sourceId, game, preventable, combat, true, null);
    }

    @Override
    public int applyDamage(Game game) {
        if (markedDamage == null) {
            return 0;
        }
        for (Counter counter : markedDamage) {
            addCounters(counter, game);
        }
        markedDamage.clear();
        return 0;
    }


    @Override
    public void removeAllDamage(Game game) {
        damage = 0;
        deathtouched = false;
    }

    protected int damagePlaneswalker(int damage, UUID sourceId, Game game, boolean preventable, boolean combat, boolean markDamage, ArrayList<UUID> appliedEffects) {
        GameEvent event = new DamagePlaneswalkerEvent(objectId, sourceId, controllerId, damage, preventable, combat);
        event.setAppliedEffects(appliedEffects);
        if (!game.replaceEvent(event)) {
            int actualDamage = event.getAmount();
            if (actualDamage > 0) {
                int countersToRemove = actualDamage;
                if (countersToRemove > getCounters().getCount(CounterType.LOYALTY)) {
                    countersToRemove = getCounters().getCount(CounterType.LOYALTY);
                }
                getCounters().removeCounter(CounterType.LOYALTY, countersToRemove);
                game.fireEvent(new DamagedPlaneswalkerEvent(objectId, sourceId, controllerId, actualDamage, combat));
                return actualDamage;
            }
        }
        return 0;
    }

    protected int damageCreature(int damage, UUID sourceId, Game game, boolean preventable, boolean combat, boolean markDamage, ArrayList<UUID> appliedEffects) {
        GameEvent event = new DamageCreatureEvent(objectId, sourceId, controllerId, damage, preventable, combat);
        event.setAppliedEffects(appliedEffects);
        if (!game.replaceEvent(event)) {
            int actualDamage = checkProtectionAbilities(event, sourceId, game);
            if (actualDamage > 0) {
                //Permanent source = game.getPermanent(sourceId);
                MageObject source = game.getObject(sourceId);
                if (source != null && (source.getAbilities().containsKey(InfectAbility.getInstance().getId())
                        || source.getAbilities().containsKey(WitherAbility.getInstance().getId()))) {
                    if (markDamage) {
                        // mark damage only
                        markDamage(CounterType.M1M1.createInstance(actualDamage));
                    } else {
                        // deal damage immediately
                        addCounters(CounterType.M1M1.createInstance(actualDamage), game);
                    }
                } else {
                    this.damage += actualDamage;
                }
                game.fireEvent(new DamagedCreatureEvent(objectId, sourceId, controllerId, actualDamage, combat));
                return actualDamage;
            }
        }
        return 0;
    }

    private int checkProtectionAbilities(GameEvent event, UUID sourceId, Game game) {
        MageObject source = game.getObject(sourceId);
        if (source != null && hasProtectionFrom(source, game)) {
            GameEvent preventEvent = new GameEvent(GameEvent.EventType.PREVENT_DAMAGE, this.objectId, sourceId, this.controllerId, event.getAmount(), false);
            if (!game.replaceEvent(preventEvent)) {
                int preventedDamage = event.getAmount();
                event.setAmount(0);
                game.fireEvent(GameEvent.getEvent(GameEvent.EventType.PREVENTED_DAMAGE, this.objectId, sourceId, this.controllerId, preventedDamage));
                return 0;
            }
        }
        return event.getAmount();
    }

    private void markDamage(Counter counter) {
        if (markedDamage == null) {
            markedDamage = new ArrayList<>();
        }
        markedDamage.add(counter);
    }


    @Override
    public void entersBattlefield(UUID sourceId, Game game, Zone fromZone, boolean fireEvent) {
        controlledFromStartOfControllerTurn = false;
        if (this.isFaceDown(game)) {
            // remove some attributes here, bceause first apply effects comes later otherwise abilities (e.g. color related) will unintended trigger
            MorphAbility.setPermanentToFaceDownCreature(this);
        }
        EntersTheBattlefieldEvent event = new EntersTheBattlefieldEvent(this, sourceId, getControllerId(), fromZone);
        if (!game.replaceEvent(event)) {
            if (fireEvent) {
                if (sourceId == null) { // play lands
                    game.fireEvent(event);
                } else { // from effects
                    game.addSimultaneousEvent(event);
                }
            }
        }
    }

    @Override
    public boolean canBeTargetedBy(MageObject source, UUID sourceControllerId, Game game) {
        if (source != null) {
            if (abilities.containsKey(ShroudAbility.getInstance().getId())) {
                return false;
            }
            if (abilities.containsKey(HexproofAbility.getInstance().getId())) {
                if (game.getPlayer(this.getControllerId()).hasOpponent(sourceControllerId, game) &&
                        !game.getContinuousEffects().asThough(this.getId(), AsThoughEffectType.HEXPROOF, sourceControllerId, game)) {
                    return false;
                }
            }
            if (hasProtectionFrom(source, game)) {
                return false;
            }
            // needed to get the correct possible targets if target rule modification effects are active
            // e.g. Fiendslayer Paladin tried to target with Ultimate Price
            if (game.getContinuousEffects().preventedByRuleModification(GameEvent.getEvent(EventType.TARGET, this.getId(), source.getId(), sourceControllerId), null, game, true)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean hasProtectionFrom(MageObject source, Game game) {
        for (ProtectionAbility ability : abilities.getProtectionAbilities()) {
            if (!ability.canTarget(source, game)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean cantBeEnchantedBy(MageObject source, Game game) {
        for (ProtectionAbility ability : abilities.getProtectionAbilities()) {
            if (!(source.getSubtype().contains("Aura") && !ability.removesAuras()) && !ability.canTarget(source, game)) {
                return true;
            }
        }
        return false;
    }

    protected boolean canDamage(MageObject source, Game game) {
        //noxx: having protection doesn't prevents from dealing damage
        // instead it adds damage prevention
        //return (!hasProtectionFrom(source, game));
        return true;
    }

    @Override
    public boolean destroy(UUID sourceId, Game game, boolean noRegen) {
        //20091005 - 701.6
        if(abilities.containsKey(IndestructibleAbility.getInstance().getId())) {
            return false;
        }

        if (!game.replaceEvent(GameEvent.getEvent(EventType.DESTROY_PERMANENT, objectId, sourceId, controllerId, noRegen ? 1 : 0))) {
            if (moveToZone(Zone.GRAVEYARD, sourceId, game, false)) {
                if (!game.isSimulation()) {
                    String logName;
                    Card card = game.getCard(this.getId());
                    if (card != null) {
                        logName = card.getLogName();
                    } else {
                        logName = this.getLogName();
                    }
                    if (this.getCardType().contains(CardType.CREATURE)) {
                        game.informPlayers(logName +" died");
                    } else {
                        game.informPlayers(logName + " was destroyed");
                    }
                }
                game.fireEvent(GameEvent.getEvent(EventType.DESTROYED_PERMANENT, objectId, sourceId, controllerId));
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean sacrifice(UUID sourceId, Game game) {
        //20091005 - 701.13
        if (!game.replaceEvent(GameEvent.getEvent(EventType.SACRIFICE_PERMANENT, objectId, sourceId, controllerId))) {
            // Commander replacement effect or Rest in Peace (exile instead of graveyard) in play does not prevent successful sacrifice
            // so the return value of the moveToZone is not taken into account here
            moveToZone(Zone.GRAVEYARD, sourceId, game, false);
            Player player = game.getPlayer(getControllerId());
            if (player != null && !game.isSimulation()) {
                game.informPlayers(new StringBuilder(player.getName()).append(" sacrificed ").append(this.getLogName()).toString());
            }
            game.fireEvent(GameEvent.getEvent(EventType.SACRIFICED_PERMANENT, objectId, sourceId, controllerId));
            return true;
        }
        return false;
    }

    @Override
    public boolean regenerate(UUID sourceId, Game game) {
        //20110930 - 701.12
        if (!game.replaceEvent(GameEvent.getEvent(EventType.REGENERATE, objectId, sourceId, controllerId))) {
            this.tap(game);
            this.removeFromCombat(game);
            this.removeAllDamage(game);
            game.fireEvent(GameEvent.getEvent(EventType.REGENERATED, objectId, sourceId, controllerId));
            return true;
        }
        return false;
    }

    @Override
    public void addPower(int power) {
        this.power.boostValue(power);
    }

    @Override
    public void addToughness(int toughness) {
        this.toughness.boostValue(toughness);
    }

    protected void fireEvent(EventType eventType, Game game) {
        game.fireEvent(GameEvent.getEvent(eventType, this.objectId, ownerId)); // controllerId seems to me more logical (LevelX2)
    }

    protected boolean replaceEvent(EventType eventType, Game game) {
        return game.replaceEvent(GameEvent.getEvent(eventType, this.objectId, ownerId));// controllerId seems to me more logical (LevelX2)
    }



    @Override
    public boolean canAttack(Game game) {
        return canAttack(null, game);
    }

    @Override
    public boolean canAttack(UUID defenderId, Game game) {
        if (tapped) {
            return false;
        }
        if (hasSummoningSickness()) {
            return false;
        }
        //20101001 - 508.1c
        if (defenderId == null) {
            boolean oneCanBeAttacked = false;
            for (UUID defenderToCheckId: game.getCombat().getDefenders()) {
                if (canAttackCheckRestrictionEffects(defenderToCheckId, game)) {
                    oneCanBeAttacked = true;
                    break;
                }
            }
            if (!oneCanBeAttacked) {
                return false;
            }
        } else {
            if (!canAttackCheckRestrictionEffects(defenderId, game)) {
                return false;
            }
        }

        return !abilities.containsKey(DefenderAbility.getInstance().getId())
                || game.getContinuousEffects().asThough(this.objectId, AsThoughEffectType.ATTACK, this.getControllerId(), game);
    }


    private boolean canAttackCheckRestrictionEffects(UUID defenderId, Game game) {
        //20101001 - 508.1c
        for (Map.Entry<RestrictionEffect, HashSet<Ability>> effectEntry: game.getContinuousEffects().getApplicableRestrictionEffects(this, game).entrySet()) {
            if (!effectEntry.getKey().canAttack(game)) {
                return false;
            }
            for (Ability ability :effectEntry.getValue()) {
                if (!effectEntry.getKey().canAttack(defenderId, ability, game)) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public boolean canBlock(UUID attackerId, Game game) {
        if (tapped && !game.getState().getContinuousEffects().asThough(this.getId(), AsThoughEffectType.BLOCK_TAPPED, this.getControllerId(), game)) {
            return false;
        }
        Permanent attacker = game.getPermanent(attackerId);
        if (attacker == null) {
            return false;
        }
        // controller of attacking permanent must be an opponent
        if (!game.getPlayer(this.getControllerId()).hasOpponent(attacker.getControllerId(), game)) {
            return false;
        }
        //20101001 - 509.1b
        // check blocker restrictions
        for (Map.Entry<RestrictionEffect, HashSet<Ability>> entry: game.getContinuousEffects().getApplicableRestrictionEffects(this, game).entrySet()) {
            for (Ability ability : entry.getValue()) {
                if (!entry.getKey().canBlock(attacker, this, ability, game)) {
                    return false;
                }
            }
        }
        // check also attacker's restriction effects
        for (Map.Entry<RestrictionEffect, HashSet<Ability>> restrictionEntry: game.getContinuousEffects().getApplicableRestrictionEffects(attacker, game).entrySet()) {
            for (Ability ability : restrictionEntry.getValue()) {
                if (!restrictionEntry.getKey().canBeBlocked(attacker, this, ability, game)) {
                    return false;
                }
            }
        }
        return !attacker.hasProtectionFrom(this, game);
    }

    @Override
    public boolean canBlockAny(Game game) {
        if (tapped && !game.getState().getContinuousEffects().asThough(this.getId(), AsThoughEffectType.BLOCK_TAPPED, this.getControllerId(), game)) {
            return false;
        }

        //20101001 - 509.1b
        for (Map.Entry entry: game.getContinuousEffects().getApplicableRestrictionEffects(this, game).entrySet()) {
            RestrictionEffect effect = (RestrictionEffect)entry.getKey();
            for (Ability ability : (HashSet<Ability>) entry.getValue()) {
                if (!effect.canBlock(null, this, ability, game)) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Checks by restriction effects if the permanent can use activated abilities
     *
     * @param game
     * @return true - permanent can use activated abilities
     */
    @Override
    public boolean canUseActivatedAbilities(Game game) {
        for (Map.Entry entry: game.getContinuousEffects().getApplicableRestrictionEffects(this, game).entrySet()) {
            RestrictionEffect effect = (RestrictionEffect)entry.getKey();
            for (Ability ability : (HashSet<Ability>) entry.getValue()) {
                if (!effect.canUseActivatedAbilities(this, ability, game)) {
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public void setAttacking(boolean attacking) {
        this.attacking = attacking;
    }

    @Override
    public void setBlocking(int blocking) {
        this.blocking = blocking;
    }

    @Override
    public void setMaxBlocks(int maxBlocks) {
        this.maxBlocks = maxBlocks;
    }

    @Override
    public void setMinBlockedBy(int minBlockedBy) {
        this.minBlockedBy = minBlockedBy;
    }


    @Override
    public void setMaxBlockedBy(int maxBlockedBy) {
        this.maxBlockedBy = maxBlockedBy;
    }

    @Override
    public boolean removeFromCombat(Game game) {
        return removeFromCombat(game, true);
    }

    @Override
    public boolean removeFromCombat(Game game, boolean withInfo) {
        if (this.isAttacking() || this.blocking > 0) {
            if (game.getCombat().removeFromCombat(objectId, game) && withInfo && !game.isSimulation()) {
                game.informPlayers(new StringBuilder(this.getLogName()).append(" removed from combat").toString());
            }
        }
        return true;
    }

    @Override
    public void setRemovedFromCombat(boolean removedFromCombat) {
        this.removedFromCombat = removedFromCombat;
    }

    @Override
    public boolean imprint(UUID imprintedCard, Game game) {
        if (connectedCards.containsKey("imprint")) {
            this.connectedCards.get("imprint").add(imprintedCard);
        } else {
            List<UUID> imprinted = new ArrayList<>();
            imprinted.add(imprintedCard);
            this.connectedCards.put("imprint", imprinted);
        }
        return true;
    }

    @Override
    public boolean clearImprinted(Game game) {
        this.connectedCards.put("imprint", new ArrayList<UUID>());
        return true;
    }

    @Override
    public List<UUID> getImprinted() {
        if (this.connectedCards.containsKey("imprint")) {
            return this.connectedCards.get("imprint");
        } else {
            return emptyList;
        }
    }

    @Override
    public HashSet<MageObjectReference> getDealtDamageByThisTurn() {
        if (dealtDamageByThisTurn == null) {
            return new HashSet<>();
        }
        return dealtDamageByThisTurn;
    }

    @Override
    public boolean isTransformed() {
        return this.transformed;
    }

    @Override
    public void setTransformed(boolean value) {
        this.transformed = value;
    }

    @Override
    public boolean isMonstrous() {
        return this.monstrous;
    }

    @Override
    public void setMonstrous(boolean value) {
        this.monstrous = value;
    }

    @Override
    public void setPairedCard(UUID pairedCard) {
        this.pairedCard = pairedCard;
    }

    @Override
    public UUID getPairedCard() {
        return pairedCard;
    }

    @Override
    public void clearPairedCard() {
        this.pairedCard = null;
    }

    @Override
    public String getLogName() {
        if (name.isEmpty()) {
            if (faceDown) {
                return "face down creature";
            } else {
                return "a creature without name";
            }
        }
        return name;
    }

    @Override
    public boolean isManifested() {
        return manifested;
    }

    @Override
    public void setManifested(boolean value) {
        manifested = value;
    }

    @Override
    public boolean isMorphed() {
        return morphed;
    }

    @Override
    public void setMorphed(boolean value) {
        morphed = value;
    }

    @Override
    public void setCardNumber(int cid) {
        this.cardNumber = cid;
    }

    @Override
    public void setExpansionSetCode(String expansionSetCode) {
        this.expansionSetCode = expansionSetCode;
    }

    @Override
    public void setRarity(Rarity rarity) {
        this.rarity = rarity;
    }

    @Override
    public void setFlipCard(boolean flipCard) {
        this.flipCard = flipCard;
    }

    @Override
    public void setFlipCardName(String flipCardName) {
        this.flipCardName = flipCardName;
    }

    @Override
    public void setSecondCardFace(Card card) {
        this.secondSideCard = card;
    }

    @Override
    public boolean fight(Permanent fightTarget, Ability source, Game game) {
        game.fireEvent(GameEvent.getEvent(GameEvent.EventType.FIGHTED_PERMANENT, fightTarget.getId(), getId(), source.getControllerId()));
        game.fireEvent(GameEvent.getEvent(GameEvent.EventType.FIGHTED_PERMANENT, getId(), fightTarget.getId(), source.getControllerId()));
        damage(fightTarget.getPower().getValue(), fightTarget.getId(), game, false, true);
        fightTarget.damage(getPower().getValue(), getId(), game, false, true);
        return true;
    }
}
