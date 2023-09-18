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
import mage.game.command.Emblem;

/**
 * @author spjspj
 */
public final class ElspethKnightErrantEmblem extends Emblem {

    public ElspethKnightErrantEmblem() {
        super("Emblem Elspeth");
        FilterControlledPermanent filter = new FilterControlledPermanent("Artifacts, creatures, enchantments, and lands you control");
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.CREATURE.getPredicate(),
                CardType.ENCHANTMENT.getPredicate(),
                CardType.LAND.getPredicate()));
        Effect effect = new GainAbilityAllEffect(IndestructibleAbility.getInstance(), Duration.WhileOnBattlefield, filter, false);
        effect.setText("Artifacts, creatures, enchantments, and lands you control have indestructible");
        this.getAbilities().add(new SimpleStaticAbility(Zone.COMMAND, effect));
    }

    private ElspethKnightErrantEmblem(final ElspethKnightErrantEmblem card) {
        super(card);
    }

    @Override
    public ElspethKnightErrantEmblem copy() {
        return new ElspethKnightErrantEmblem(this);
    }
}
