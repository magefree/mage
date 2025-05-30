package mage.cards.u;

import mage.abilities.effects.common.UntapAllEffect;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterNonlandPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class UnstoppablePlan extends CardImpl {

    private static final FilterPermanent filter = new FilterNonlandPermanent("nonland permanents you control");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public UnstoppablePlan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}");

        // At the beginning of your end step, untap all nonland permanents you control.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(new UntapAllEffect(filter)));
    }

    private UnstoppablePlan(final UnstoppablePlan card) {
        super(card);
    }

    @Override
    public UnstoppablePlan copy() {
        return new UnstoppablePlan(this);
    }
}
