package mage.cards.w;

import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.effects.common.UntapAllControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WildernessReclamation extends CardImpl {

    public WildernessReclamation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{G}");

        // At the beginning of your end step, untap all lands you control.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                new UntapAllControllerEffect(StaticFilters.FILTER_LANDS),
                TargetController.YOU, false
        ));
    }

    private WildernessReclamation(final WildernessReclamation card) {
        super(card);
    }

    @Override
    public WildernessReclamation copy() {
        return new WildernessReclamation(this);
    }
}
