package mage.cards.r;

import mage.abilities.common.DealtDamageAnyTriggeredAbility;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author cbrianhill
 */
public final class Repercussion extends CardImpl {

    public Repercussion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{R}{R}");

        // Whenever a creature is dealt damage, Repercussion deals that much damage to that creature's controller.
        this.addAbility(new DealtDamageAnyTriggeredAbility(
                new DamageTargetEffect(SavedDamageValue.MUCH).withTargetDescription("that creature's controller"),
                StaticFilters.FILTER_PERMANENT_A_CREATURE, SetTargetPointer.PLAYER, false));
    }

    private Repercussion(final Repercussion card) {
        super(card);
    }

    @Override
    public Repercussion copy() {
        return new Repercussion(this);
    }
}
