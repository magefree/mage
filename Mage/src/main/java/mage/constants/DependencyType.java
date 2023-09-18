package mage.constants;

/**
 * Dependency types are a part of a workaround solution to handle dependencies
 * of continuous effects.
 * <p>
 * All continuous effects can: addDependencyType -- make dependency (effect
 * makes some changes) addDependedToType -- wait another dependency (effect must
 * wait until all other effects finished)
 * <p>
 * http://magiccards.info/rule/613-interaction-of-continuous-effects.html
 * https://github.com/magefree/mage/issues/1259
 *
 * @author LevelX2
 */
public enum DependencyType {
    AuraAddingRemoving,
    ArtifactAddingRemoving,
    AddingAbility,
    AddingCreatureType,
    BecomeNonbasicLand,
    BecomeForest,
    BecomeIsland,
    BecomeMountain,
    BecomePlains,
    BecomeSwamp,
    BecomeCreature,
    EnchantmentAddingRemoving,
    LooseDefenderEffect
}
