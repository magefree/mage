
package mage.constants;

/**
 * Dependency types are a part of a workaround solution to handle dependencies
 * of continuous effects.
 *
 * http://magiccards.info/rule/613-interaction-of-continuous-effects.html
 *
 * https://github.com/magefree/mage/issues/1259
 *
 *
 * @author LevelX2
 */
public enum DependencyType {
    AuraAddingRemoving,
    ArtifactAddingRemoving,
    AddingAbility,
    BecomeForest,
    BecomeIsland,
    BecomeMountain,
    BecomePlains,
    BecomeSwamp,
    BecomeCreature,
    EnchantmentAddingRemoving,
    LooseDefenderEffect
}