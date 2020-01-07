package mage.cards.c;

import mage.abilities.common.BecomesBlockedAllTriggeredAbility;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CunningEvasion extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("a creature you control");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public CunningEvasion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{U}");

        // Whenever a creature you control becomes blocked, you may return it to its owner's hand.
        this.addAbility(new BecomesBlockedAllTriggeredAbility(
                new ReturnToHandTargetEffect().setText("return it to its owner's hand"),
                true, filter, true
        ));
    }

    private CunningEvasion(final CunningEvasion card) {
        super(card);
    }

    @Override
    public CunningEvasion copy() {
        return new CunningEvasion(this);
    }
}
