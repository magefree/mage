
package mage.abilities.effects;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashSet;
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
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.game.Game;
import mage.players.Player;

/**
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
    protected EnumSet<DependencyType> dependencyTypes; // this effect has the dependencyTypes defined here
    protected EnumSet<DependencyType> dependendToTypes; // this effect is dependent to this types
    /*
     A Characteristic Defining Ability (CDA) is an ability that defines a characteristic of a card or token.
     There are 3 specific rules that distinguish a CDA from other abilities.
     1) A CDA can only define a characteristic of either the card or token it comes from.
     2) A CDA can not be triggered, activated, or conditional.
     3) A CDA must define a characteristic. Usually color, power and/or toughness, or sub-type.
     */
    protected boolean characterDefining = false;

    // until your next turn
    protected int startingTurn;
    protected UUID startingControllerId;

    public ContinuousEffectImpl(Duration duration, Outcome outcome) {
        super(outcome);
        this.duration = duration;
        this.order = 0;
        this.effectType = EffectType.CONTINUOUS;
        this.dependencyTypes = EnumSet.noneOf(DependencyType.class);
        this.dependendToTypes = EnumSet.noneOf(DependencyType.class);
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
        this.dependendToTypes = effect.dependendToTypes;
        this.characterDefining = effect.characterDefining;
    }

    @Override
    public void setDuration(Duration duration) {
        this.duration = duration;
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
        this.used = true; // to prevent further usage before effect is removed
        this.discarded = true;
    }

    @Override
    public void init(Ability source, Game game) {
        targetPointer.init(game, source);
        //20100716 - 611.2c
        if (AbilityType.ACTIVATED == source.getAbilityType()
                || AbilityType.SPELL == source.getAbilityType()
                || AbilityType.TRIGGERED == source.getAbilityType()) {
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
            } else if (hasLayer(Layer.CopyEffects_1) || hasLayer(Layer.ControlChangingEffects_2) || hasLayer(Layer.TextChangingEffects_3)
                    || hasLayer(Layer.TypeChangingEffects_4) || hasLayer(Layer.ColorChangingEffects_5) || hasLayer(Layer.AbilityAddingRemovingEffects_6)
                    || hasLayer(Layer.PTChangingEffects_7)) {
                this.affectedObjectsSet = true;
            }
        }
        startingTurn = game.getTurnNum();
        startingControllerId = source.getControllerId();
    }

    @Override
    public boolean isInactive(Ability source, Game game) {
        if (duration == Duration.UntilYourNextTurn) {
            Player player = game.getPlayer(startingControllerId);
            if (player != null) {
                if (player.isInGame()) {
                    return game.isActivePlayer(startingControllerId) && game.getTurnNum() != startingTurn;
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

    public boolean isCharacterDefining() {
        return characterDefining;
    }

    public void setCharacterDefining(boolean characterDefining) {
        this.characterDefining = characterDefining;
    }

    @Override
    public Set<UUID> isDependentTo(List<ContinuousEffect> allEffectsInLayer) {
        Set<UUID> dependentToEffects = new HashSet<UUID>();
        if (dependendToTypes != null) {
            for (ContinuousEffect effect : allEffectsInLayer) {
                if (!effect.getId().equals(this.getId())) {
                    for (DependencyType dependencyType : effect.getDependencyTypes()) {
                        if (dependendToTypes.contains(dependencyType)) {
                            dependentToEffects.add(effect.getId());
                            break;
                        }
                    }
                }
            }
        }
        return dependentToEffects;
        /*
            return allEffectsInLayer.stream()
                    .filter(effect -> effect.getDependencyTypes().contains(dependendToTypes))
                    .map(Effect::getId)
                    .collect(Collectors.toSet());

        }
        return new HashSet<>();*/
    }

    @Override
    public EnumSet<DependencyType> getDependencyTypes() {
        return dependencyTypes;
    }

    @Override
    public void addDependencyType(DependencyType dependencyType) {
        dependencyTypes.add(dependencyType);
    }

    @Override
    public void setDependedToType(DependencyType dependencyType) {
        dependendToTypes.clear();
        dependendToTypes.add(dependencyType);
    }

    @Override
    public void addDependedToType(DependencyType dependencyType) {
        dependendToTypes.add(dependencyType);
    }

}
