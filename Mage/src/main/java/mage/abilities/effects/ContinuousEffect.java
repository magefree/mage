package mage.abilities.effects;

import mage.MageItem;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.constants.DependencyType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.SubLayer;
import mage.game.Game;
import mage.target.targetpointer.TargetPointer;

import java.util.*;

/**
 * @author BetaSteward_at_googlemail.com
 */
public interface ContinuousEffect extends Effect {

    boolean isUsed();

    boolean isDiscarded();

    void discard();

    ContinuousEffect setDuration(Duration duration);

    Duration getDuration();

    long getOrder();

    void setOrder(long order);

    boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game);

    /**
     * Applies the effect to the passed in list of objects. This method should only contain logic for applying to
     * the objects. All object filtering should be done in {@link #queryAffectedObjects} before passing to this function.
     */
    void applyToObjects(Layer layer, SubLayer sublayer, Ability source, Game game, List<MageItem> affectedObjects);

    /**
     * Gathers all objects the effect should apply to. Do all filtering logic in this function to avoid errors in {@link #applyToObjects}.
     * @return true if adding any objects to the affectedObjects list, otherwise return false
     */
    boolean queryAffectedObjects(Layer layer, Ability source, Game game, List<MageItem> affectedObjects);

    boolean hasLayer(Layer layer);

    boolean isInactive(Ability source, Game game);

    /**
     * Init ability data like ZCC or targets on first check in game cycle (ApplyEffects)
     * <p>
     * Warning, if you setup target pointer in init then must call super.init at the end (after all choices)
     */
    void init(Ability source, Game game);

    void init(Ability source, Game game, UUID activePlayerId);

    Layer getLayer();

    SubLayer getSublayer();

    List<MageObjectReference> getAffectedObjects();

    Set<UUID> isDependentTo(List<ContinuousEffect> allEffectsInLayer);

    EnumSet<DependencyType> getDependencyTypes();

    void addDependencyType(DependencyType dependencyType);

    void setDependedToType(DependencyType dependencyType);

    EnumSet<DependencyType> getDependedToTypes();

    void addDependedToType(DependencyType dependencyType);

    void setStartingControllerAndTurnNum(Game game, UUID startingController, UUID activePlayerId);

    UUID getStartingController();

    boolean isYourNextTurn(Game game);

    boolean isYourNextEndStep(Game game);

    boolean isYourNextUpkeepStep(Game game);

    @Override
    ContinuousEffect copy();

    boolean isTemporary();

    void setTemporary(boolean temporary);

    @Override
    ContinuousEffect setTargetPointer(TargetPointer targetPointer);
}
