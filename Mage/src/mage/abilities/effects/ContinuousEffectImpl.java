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
package mage.abilities.effects;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.MageSingleton;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.DomainValue;
import mage.abilities.dynamicvalue.common.SignInversionDynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.constants.AbilityType;
import mage.constants.DependencyType;
import mage.constants.Duration;
import mage.constants.EffectType;
import mage.constants.Layer;
import static mage.constants.Layer.AbilityAddingRemovingEffects_6;
import static mage.constants.Layer.ColorChangingEffects_5;
import static mage.constants.Layer.ControlChangingEffects_2;
import static mage.constants.Layer.CopyEffects_1;
import static mage.constants.Layer.PTChangingEffects_7;
import static mage.constants.Layer.TextChangingEffects_3;
import static mage.constants.Layer.TypeChangingEffects_4;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public abstract class ContinuousEffectImpl extends EffectImpl implements ContinuousEffect {

    protected Duration duration;
    protected Layer layer;
    protected SubLayer sublayer;
    protected long order;
    protected boolean used = false;
    protected boolean discarded = false; // for manual effect discard
    protected boolean affectedObjectsSet = false;
    protected List<MageObjectReference> affectedObjectList = new ArrayList<>();
    protected boolean temporary = false;
    protected EnumSet<DependencyType> dependencyTypes;

    // until your next turn
    protected int startingTurn;
    protected UUID startingControllerId;

    public ContinuousEffectImpl(Duration duration, Outcome outcome) {
        super(outcome);
        this.duration = duration;
        this.order = 0;
        this.effectType = EffectType.CONTINUOUS;
        this.dependencyTypes = EnumSet.noneOf(DependencyType.class);
    }

    public ContinuousEffectImpl(Duration duration, Layer layer, SubLayer sublayer, Outcome outcome) {
        this(duration, outcome);
        this.layer = layer;
        this.sublayer = sublayer;
    }

    public ContinuousEffectImpl(final ContinuousEffectImpl effect) {
        super(effect);
        this.duration = effect.duration;
        this.layer = effect.layer;
        this.sublayer = effect.sublayer;
        this.order = effect.order;
        this.used = effect.used;
        this.discarded = effect.discarded;
        this.affectedObjectsSet = effect.affectedObjectsSet;
        this.affectedObjectList.addAll(effect.affectedObjectList);
        this.temporary = effect.temporary;
        this.startingTurn = effect.startingTurn;
        this.startingControllerId = effect.startingControllerId;
        this.dependencyTypes = effect.dependencyTypes;
    }

    @Override
    public Duration getDuration() {
        return duration;
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        if (this.layer == layer && this.sublayer == sublayer) {
            return apply(game, source);
        }
        return false;
    }

    @Override
    public long getOrder() {
        return order;
    }

    @Override
    public void setOrder(long order) {
        this.order = order;
    }

    @Override
    public void newId() {
        if (!(this instanceof MageSingleton)) {
            this.id = UUID.randomUUID();
        }
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return this.layer == layer;
    }

    @Override
    public boolean isUsed() {
        return used;
    }

    @Override
    public boolean isDiscarded() {
        return discarded;
    }

    /**
     * Sets the discarded state of the effect. So it will be removed on next
     * check.
     */
    @Override
    public void discard() {
        this.discarded = true;
    }

    @Override
    public void init(Ability source, Game game) {
        targetPointer.init(game, source);
        //20100716 - 611.2c
        if (AbilityType.ACTIVATED.equals(source.getAbilityType())
                || AbilityType.SPELL.equals(source.getAbilityType())
                || AbilityType.TRIGGERED.equals(source.getAbilityType())) {
            if (layer != null) {
                switch (layer) {
                    case CopyEffects_1:
                    case ControlChangingEffects_2:
                    case TextChangingEffects_3:
                    case TypeChangingEffects_4:
                    case ColorChangingEffects_5:
                    case AbilityAddingRemovingEffects_6:
                    case PTChangingEffects_7:
                        this.affectedObjectsSet = true;
                }
            } else {
                if (hasLayer(Layer.CopyEffects_1) || hasLayer(Layer.ControlChangingEffects_2) || hasLayer(Layer.TextChangingEffects_3)
                        || hasLayer(Layer.TypeChangingEffects_4) || hasLayer(Layer.ColorChangingEffects_5) || hasLayer(Layer.AbilityAddingRemovingEffects_6)
                        || hasLayer(Layer.PTChangingEffects_7)) {
                    this.affectedObjectsSet = true;
                }
            }
        }
        startingTurn = game.getTurnNum();
        startingControllerId = source.getControllerId();
    }

    @Override
    public boolean isInactive(Ability source, Game game) {
        if (duration.equals(Duration.UntilYourNextTurn)) {
            Player player = game.getPlayer(startingControllerId);
            if (player != null) {
                if (player.isInGame()) {
                    return game.getActivePlayerId().equals(startingControllerId) && game.getTurnNum() != startingTurn;
                }
                return player.hasReachedNextTurnAfterLeaving();
            }
            return true;
        }
        return false;
    }

    @Override
    public Layer getLayer() {
        return layer;
    }

    @Override
    public SubLayer getSublayer() {
        return sublayer;
    }

    @Override
    public void overrideRuleText(String text) {
        this.staticText = text;
    }

    protected static boolean isCanKill(DynamicValue toughness) {
        if (toughness instanceof StaticValue) {
            return toughness.calculate(null, null, null) < 0;
        }
        if (toughness instanceof SignInversionDynamicValue) {
            // count this class as used for "-{something_positive}"
            return true;
        }
        if (toughness instanceof DomainValue) {
            return ((DomainValue) toughness).getAmount() < 0;
        }
        return false;
    }

    @Override
    public List<MageObjectReference> getAffectedObjects() {
        return affectedObjectList;
    }

    /**
     * Returns the status if the effect is temporary added to the
     * ContinuousEffects
     *
     * @return
     */
    @Override
    public boolean isTemporary() {
        return temporary;
    }

    @Override
    public void setTemporary(boolean temporary) {
        this.temporary = temporary;
    }

    @Override
    public Set<UUID> isDependentTo(List<ContinuousEffect> allEffectsInLayer) {
        return null;
    }

    @Override
    public EnumSet<DependencyType> getDependencyTypes() {
        return dependencyTypes;
    }

}
