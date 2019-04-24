
package mage.game.command.emblems;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.command.Emblem;

/**
 *
 * @author spjspj
 */
public final class ElspethKnightErrantEmblem extends Emblem {

    public ElspethKnightErrantEmblem() {
        this.setName("Emblem Elspeth");
        FilterControlledPermanent filter = new FilterControlledPermanent("Artifacts, creatures, enchantments, and lands you control");
        filter.add(Predicates.or(
                new CardTypePredicate(CardType.ARTIFACT),
                new CardTypePredicate(CardType.CREATURE),
                new CardTypePredicate(CardType.ENCHANTMENT),
                new CardTypePredicate(CardType.LAND)));
        Effect effect = new GainAbilityAllEffect(IndestructibleAbility.getInstance(), Duration.WhileOnBattlefield, filter, false);
        effect.setText("Artifacts, creatures, enchantments, and lands you control are indestructible");
        this.getAbilities().add(new SimpleStaticAbility(Zone.COMMAND, effect));
        this.setExpansionSetCodeForImage("MMA");
    }
}
