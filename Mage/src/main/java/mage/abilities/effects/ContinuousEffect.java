package mage.abilities.effects;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.constants.DependencyType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.SubLayer;
import mage.game.Game;
import mage.target.targetpointer.TargetPointer;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

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

    boolean hasLayer(Layer layer);

    boolean isInactive(Ability source, Game game);

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

    @Override
    void newId();

    @Override
    ContinuousEffect copy();

    boolean isTemporary();

    void setTemporary(boolean temporary);

    @Override
    ContinuousEffect setTargetPointer(TargetPointer targetPointer);
}
