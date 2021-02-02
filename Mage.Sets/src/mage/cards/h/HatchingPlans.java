package mage.cards.h;

import mage.abilities.common.PutIntoGraveFromBattlefieldSourceTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author Loki
 */
public final class HatchingPlans extends CardImpl {

    public HatchingPlans(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{U}");

        // When Hatching Plans is put into a graveyard from the battlefield, draw three cards.
        this.addAbility(new PutIntoGraveFromBattlefieldSourceTriggeredAbility(new DrawCardSourceControllerEffect(3)));
    }

    private HatchingPlans(final HatchingPlans card) {
        super(card);
    }

    @Override
    public HatchingPlans copy() {
        return new HatchingPlans(this);
    }

}
