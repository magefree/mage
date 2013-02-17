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
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import mage.Constants;
import mage.Constants.AsThoughEffectType;
import mage.Constants.CardType;
import mage.Constants.Zone;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.HexproofAbility;
import mage.abilities.keyword.InfectAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.abilities.keyword.ShroudAbility;
import mage.abilities.keyword.WitherAbility;
import mage.cards.CardImpl;
import mage.counters.Counter;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.DamageCreatureEvent;
import mage.game.events.DamagePlaneswalkerEvent;
import mage.game.events.DamagedCreatureEvent;
import mage.game.events.DamagedPlaneswalkerEvent;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.players.Player;


/**
 * @author BetaSteward_at_googlemail.com
 */
public abstract class PermanentImpl<T extends PermanentImpl<T>> extends CardImpl<T> implements Permanent {

    protected boolean tapped;
    protected boolean flipped;
    protected boolean transformed;
    protected UUID originalControllerId;
    protected UUID controllerId;
    protected UUID beforeResetControllerId;
    protected boolean controllerChanged;
    protected int damage;
    protected boolean controlledFromStartOfControllerTurn;
    protected int turnsOnBattlefield;
    protected boolean phasedIn = true;
    protected boolean faceUp = true;
    protected boolean attacking;
    protected int blocking;
    protected int maxBlocks = 1;
    protected int minBlockedBy = 1;
    protected boolean loyaltyUsed;
    protected boolean deathtouched;
    protected List<UUID> attachments = new ArrayList<UUID>();
    protected Map<String, List<UUID>> connectedCards = new HashMap<String, List<UUID>>();
    protected List<UUID> dealtDamageByThisTurn;
    protected UUID attachedTo;
    protected UUID pairedCard;
    protected List<Counter> markedDamage;

    private static final List<UUID> emptyList = Collections.unmodifiableList(new ArrayList<UUID>());

    public PermanentImpl(UUID ownerId, UUID controllerId, String name) {
        super(ownerId, name);
        this.originalControllerId = controllerId;
        this.controllerId = controllerId;
    }

    public PermanentImpl(UUID id, UUID ownerId, UUID controllerId, String name) {
        super(id, ownerId, name);
        this.originalControllerId = controllerId;
        this.controllerId = controllerId;
    }

    public PermanentImpl(final PermanentImpl<T> permanent) {
        super(permanent);
        this.tapped = permanent.tapped;
        this.flipped = permanent.flipped;
        this.originalControllerId = permanent.originalControllerId;
        this.controllerId = permanent.controllerId;
        this.damage = permanent.damage;
        this.controlledFromStartOfControllerTurn = permanent.controlledFromStartOfControllerTurn;
        this.turnsOnBattlefield = permanent.turnsOnBattlefield;
        this.phasedIn = permanent.phasedIn;
        this.faceUp = permanent.faceUp;
        this.attacking = permanent.attacking;
        this.blocking = permanent.blocking;
        this.maxBlocks = permanent.maxBlocks;
        this.loyaltyUsed = permanent.loyaltyUsed;
        this.deathtouched = permanent.deathtouched;
        this.attachments.addAll(permanent.attachments);
        for (Map.Entry<String, List<UUID>> entry: permanent.connectedCards.entrySet()) {
            this.connectedCards.put(entry.getKey(), entry.getValue());
        }
        if (permanent.dealtDamageByThisTurn != null) {
            dealtDamageByThisTurn = new ArrayList<UUID>(permanent.dealtDamageByThisTurn);
            if (permanent.markedDamage != null) {
                markedDamage = new ArrayList<Counter>();
                for (Counter counter : permanent.markedDamage) {
                    markedDamage.add(counter.copy());
                }
            }
        }
        this.attachedTo = permanent.attachedTo;
        this.minBlockedBy = permanent.minBlockedBy;
        this.transformed = permanent.transformed;
        this.pairedCard = permanent.pairedCard;
        this.copy = permanent.copy;
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
    public void reset(Game game) {
        this.beforeResetControllerId = this.controllerId;
        this.controllerId = originalControllerId;
        if (!controllerId.equals(beforeResetControllerId)) {
            controllerChanged = true;
        }
        else {
            controllerChanged = false;
        }
        this.maxBlocks = 1;
        this.minBlockedBy = 1;
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
        if (!abilities.containsKey(ability.getId())) {
            Ability copyAbility = ability.copy();
            copyAbility.setControllerId(controllerId);
            copyAbility.setSourceId(objectId);
            game.getState().addAbility(copyAbility, sourceId, this);
            abilities.add(copyAbility);
        }
    }
    
    @Override
    public void removeAllAbilities(UUID sourceId, Game game) {
        getAbilities().clear();
        // removes abilities that were gained from abilities of this permanent
        game.getContinuousEffects().removeGainedEffectsForSource(this.getId());
        // remove gained triggered abilities
        game.getState().resetForSourceId(this.getId());
    }

    @Override
    public void addCounters(String name, int amount, Game game, ArrayList<UUID> appliedEffects) {
        for (int i = 0; i < amount; i++) {
            GameEvent event = GameEvent.getEvent(GameEvent.EventType.ADD_COUNTER, objectId, controllerId, name, 1);
            event.setAppliedEffects(appliedEffects);
            if (!game.replaceEvent(event)) {
                counters.addCounter(name, 1);
                game.fireEvent(GameEvent.getEvent(GameEvent.EventType.COUNTER_ADDED, objectId, controllerId, name, 1));
            }
        }
    }

    @Override
    public void addCounters(Counter counter, Game game, ArrayList<UUID> appliedEffects) {
        int amount = counter.getCount();
        for (int i = 0; i < amount; i++) {
            Counter eventCounter = counter.copy();
            eventCounter.remove(amount - 1);
            GameEvent event = GameEvent.getEvent(GameEvent.EventType.ADD_COUNTER, objectId, controllerId, counter.getName(), 1);
            event.setAppliedEffects(appliedEffects);
            if (!game.replaceEvent(event)) {
                counters.addCounter(eventCounter);
                game.fireEvent(GameEvent.getEvent(GameEvent.EventType.COUNTER_ADDED, objectId, controllerId, counter.getName(), 1));
            }
        }
    }

    @Override
    public void removeCounters(String name, int amount, Game game) {
        counters.removeCounter(name, amount);
        GameEvent event = GameEvent.getEvent(GameEvent.EventType.COUNTER_REMOVED, objectId, controllerId);
        event.setData(name);
        for (int i = 0; i < amount; i++) {
            game.fireEvent(event);
        }
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
        this.loyaltyUsed = false;
        this.turnsOnBattlefield++;
        this.deathtouched = false;
        dealtDamageByThisTurn = null;
        for (Ability ability : this.abilities) {
            ability.reset(game);
        }
    }

    @Override
    public void setLoyaltyUsed(boolean used) {
        this.loyaltyUsed = used;
    }

    @Override
    public boolean isLoyaltyUsed() {
        return this.loyaltyUsed;
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
        //20100423 - 302.6
        if (!cardType.contains(CardType.CREATURE) || !hasSummoningSickness()) {
            return true;
        }
        return false;
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
                fireEvent(EventType.PHASED_OUT, game);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isFaceUp() {
        return faceUp;
    }

    @Override
    public boolean turnFaceUp(Game game) {
        //TODO: implement this
        return false;
    }

    @Override
    public boolean turnFaceDown(Game game) {
        //TODO: implement this
        return false;
    }

    public void removeSummoningSickness() {
        this.controlledFromStartOfControllerTurn = true;
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
    public UUID getControllerId() {
        return this.controllerId;
    }

    @Override
    public boolean changeControllerId(UUID controllerId, Game game) {
        if (!controllerId.equals(this.controllerId)) {
            Player newController = game.getPlayer(controllerId);
            if (newController != null && (!newController.hasLeft() || !newController.hasLost())) {
                // changeControllerId can be called by continuous effect
                // so it will lead to this.controlledFromStartOfControllerTurn set to false over and over
                // because of reset(game) method called before applying effect as state-based action
                // that changes this.controllerId to original one (actually owner)
                if (!controllerId.equals(beforeResetControllerId)) {
                    this.removeFromCombat(game);
                    this.controlledFromStartOfControllerTurn = false;
                    this.controllerChanged = true;
                } else {
                    this.controllerChanged = false;
                }
                this.controllerId = controllerId;
                this.abilities.setControllerId(controllerId);
                game.getContinuousEffects().setController(this.objectId, controllerId);
                return true;
            }
        }
        return false;
    }

    @Override
    public void checkControlChanged(Game game) {
        if (this.controllerChanged) {
            game.fireEvent(new GameEvent(EventType.LOST_CONTROL, objectId, objectId, beforeResetControllerId));
            game.fireEvent(new GameEvent(EventType.GAINED_CONTROL, objectId, objectId, controllerId));
        }
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
            List<UUID> list = new ArrayList<UUID>();
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
        if (this.attachedTo != null) {
            Permanent attachment = game.getPermanent(this.attachedTo);
            if (attachment != null) {
                attachment.removeAttachment(this.objectId, game);
            }
        }
        this.attachedTo = permanentId;
        /*
         * 20121001 613.6. Within a layer or sublayer, determining which order effects are applied in is
         *                 usually done using a timestamp system. An effect with an earlier timestamp is
         *                 applied before an effect with a later timestamp
         * 20121001 613.6d If an Aura, Equipment, or Fortification becomes attached to an object or player,
         *                 the Aura, Equipment, or Fortification receives a new timestamp at that time.
         */
        for (Iterator<Ability> it = this.getAbilities().iterator(); it.hasNext();) {
            Ability ability = it.next();
            for (Iterator<Effect> ite = ability.getEffects(game, Constants.EffectType.CONTINUOUS).iterator(); ite.hasNext();) {
                ContinuousEffect effect = (ContinuousEffect) ite.next();
                game.getContinuousEffects().setUniqueTimesstamp(effect);
                // It's important is to update timestamp of the copied effect in ContinuousEffects because it does the action
                for (ContinuousEffect conEffect: game.getContinuousEffects().getLayeredEffects(game)) {
                    if (conEffect.getId().equals(effect.getId())) {
                        game.getContinuousEffects().setUniqueTimesstamp(conEffect);
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
    public int damage(int damageAmount, UUID sourceId, Game game, boolean preventable, boolean combat) {
        return damage(damageAmount, sourceId, game, preventable, combat, false, null);
    }

    @Override
    public int damage(int damageAmount, UUID sourceId, Game game, boolean preventable, boolean combat, ArrayList<UUID> appliedEffects) {
        return damage(damageAmount, sourceId, game, preventable, combat, false, appliedEffects);
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
                Permanent source = game.getPermanent(sourceId);
                if (source != null && source.getAbilities().containsKey(LifelinkAbility.getInstance().getId())) {
                    Player player = game.getPlayer(source.getControllerId());
                    player.gainLife(damageAmount, game);
                }
                if (source != null && source.getAbilities().containsKey(DeathtouchAbility.getInstance().getId())) {
                    deathtouched = true;
                }
                if (dealtDamageByThisTurn == null) {
                    dealtDamageByThisTurn = new ArrayList<UUID>();
                }
                dealtDamageByThisTurn.add(sourceId);
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
            markedDamage = new ArrayList<Counter>();
        }
        markedDamage.add(counter);
    }


    @Override
    public void entersBattlefield(UUID sourceId, Game game, Zone fromZone, boolean fireEvent) {
        controlledFromStartOfControllerTurn = false;
        EntersTheBattlefieldEvent event = new EntersTheBattlefieldEvent(this, sourceId, getControllerId(), fromZone);
        if (!game.replaceEvent(event)) {
            if (fireEvent) {
                game.fireEvent(event);
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
                if (game.getOpponents(controllerId).contains(sourceControllerId) &&
                        !game.getContinuousEffects().asThough(this.getId(), AsThoughEffectType.HEXPROOF, game)) {
                    return false;
                }
            }
            if (hasProtectionFrom(source, game)) {
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

    protected boolean canDamage(MageObject source, Game game) {
        //noxx: having protection doesn't prevents from dealing damage
        // instead it adds damage prevention
        //return (!hasProtectionFrom(source, game));
        return true;
    }

    @Override
    public boolean destroy(UUID sourceId, Game game, boolean noRegen) {
        //20091005 - 701.6
        //TODO: handle noRegen
        if (!game.replaceEvent(GameEvent.getEvent(EventType.DESTROY_PERMANENT, objectId, sourceId, controllerId, noRegen ? 1 : 0))) {
            if (moveToZone(Zone.GRAVEYARD, sourceId, game, false)) {
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
            if (moveToZone(Zone.GRAVEYARD, sourceId, game, true)) {
                game.fireEvent(GameEvent.getEvent(EventType.SACRIFICED_PERMANENT, objectId, sourceId, controllerId));
                return true;
            }
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
        game.fireEvent(GameEvent.getEvent(eventType, this.objectId, ownerId));
    }

    protected boolean replaceEvent(EventType eventType, Game game) {
        return game.replaceEvent(GameEvent.getEvent(eventType, this.objectId, ownerId));
    }

    @Override
    public boolean canAttack(Game game) {
        if (tapped) {
            return false;
        }
        if (hasSummoningSickness()) {
            return false;
        }
        //20101001 - 508.1c
        for (RestrictionEffect effect : game.getContinuousEffects().getApplicableRestrictionEffects(this, game)) {
            if (!effect.canAttack(game)) {
                return false;
            }
        }
        if (abilities.containsKey(DefenderAbility.getInstance().getId()) && !game.getContinuousEffects().asThough(this.objectId, AsThoughEffectType.ATTACK, game)) {
            return false;
        }
        return true;
    }

    @Override
    public boolean canBlock(UUID attackerId, Game game) {
        if (tapped) {
            return false;
        }
        Permanent attacker = game.getPermanent(attackerId);
        //20101001 - 509.1b
        for (RestrictionEffect effect : game.getContinuousEffects().getApplicableRestrictionEffects(this, game)) {
            if (!effect.canBlock(attacker, this, game.getContinuousEffects().getAbility(effect.getId()), game)) {
                return false;
            }
        }
        // check also attacker's restriction effects
        for (RestrictionEffect effect : game.getContinuousEffects().getApplicableRestrictionEffects(attacker, game)) {
            /*if (!effect.canBlock(attacker, this, game))
                   return false;*/
            if (!effect.canBeBlocked(attacker, this, game.getContinuousEffects().getAbility(effect.getId()), game)) {
                return false;
            }
        }
        if (attacker != null && attacker.hasProtectionFrom(this, game)) {
            return false;
        }
        return true;
    }

    @Override
    public boolean canBlockAny(Game game) {
        if (tapped) {
            return false;
        }

        //20101001 - 509.1b
        for (RestrictionEffect effect : game.getContinuousEffects().getApplicableRestrictionEffects(this, game)) {
            if (!effect.canBlock(null, this, game.getContinuousEffects().getAbility(effect.getId()), game)) {
                return false;
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
    public boolean removeFromCombat(Game game) {
        game.getCombat().removeFromCombat(objectId, game);
        return true;
    }

    @Override
    public boolean imprint(UUID imprintedCard, Game game) {
        if (connectedCards.containsKey("imprint")) {
            this.connectedCards.get("imprint").add(imprintedCard);
        } else {
            List<UUID> imprinted = new ArrayList<UUID>();
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
    public List<UUID> getDealtDamageByThisTurn() {
        if (dealtDamageByThisTurn == null) {
            return emptyList;
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
}
