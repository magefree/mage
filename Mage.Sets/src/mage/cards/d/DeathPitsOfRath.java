package mage.cards.d;

import mage.abilities.common.DealtDamageAnyTriggeredAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author Plopman
 */
public final class DeathPitsOfRath extends CardImpl {

    public DeathPitsOfRath(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{B}{B}");

        // Whenever a creature is dealt damage, destroy it. It can't be regenerated.
        this.addAbility(new DealtDamageAnyTriggeredAbility(new DestroyTargetEffect(true),
                StaticFilters.FILTER_PERMANENT_A_CREATURE, SetTargetPointer.PERMANENT, false));
    }

    private DeathPitsOfRath(final DeathPitsOfRath card) {
        super(card);
    }

    @Override
    public DeathPitsOfRath copy() {
        return new DeathPitsOfRath(this);
    }
}
