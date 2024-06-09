package mage.cards.d;

import mage.abilities.common.SacrificePermanentTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.SkipDrawStepEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 *
 * @author jeffwadsworth
 */
public final class DragonAppeasement extends CardImpl {

    public DragonAppeasement(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{3}{B}{R}{G}");

        // Skip your draw step.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SkipDrawStepEffect()));

        // Whenever you sacrifice a creature, you may draw a card.
        this.addAbility(new SacrificePermanentTriggeredAbility(Zone.BATTLEFIELD,
                new DrawCardSourceControllerEffect(1), StaticFilters.FILTER_PERMANENT_CREATURE,
                TargetController.YOU, SetTargetPointer.NONE, true));

    }

    private DragonAppeasement(final DragonAppeasement card) {
        super(card);
    }

    @Override
    public DragonAppeasement copy() {
        return new DragonAppeasement(this);
    }
}
