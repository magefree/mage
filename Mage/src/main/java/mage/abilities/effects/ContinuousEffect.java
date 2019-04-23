package mage.abilities.effects;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.constants.DependencyType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.SubLayer;
import mage.game.Game;

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

    void setDuration(Duration duration);

    Duration getDuration();

    long getOrder();

    void setOrder(long order);

    boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game);

    boolean hasLayer(Layer layer);

    boolean isInactive(Ability source, Game game);

    void init(Ability source, Game game);

    Layer getLayer();

    SubLayer getSublayer();

    void overrideRuleText(String text);

    List<MageObjectReference> getAffectedObjects();

    Set<UUID> isDependentTo(List<ContinuousEffect> allEffectsInLayer);

    EnumSet<DependencyType> getDependencyTypes();

    void addDependencyType(DependencyType dependencyType);

    void setDependedToType(DependencyType dependencyType);

    void addDependedToType(DependencyType dependencyType);

    void setStartingTurnNum(Game game, UUID startingController);

    int getStartingTurnNum();

    int getNextStartingControllerTurnNum();

    UUID getStartingController();

    @Override
    void newId();

    @Override
    ContinuousEffect copy();

    boolean isTemporary();

    void setTemporary(boolean temporary);
}
