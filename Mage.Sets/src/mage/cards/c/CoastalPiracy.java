package mage.cards.c;

import mage.abilities.common.DealsDamageToAPlayerAllTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author Xavierv3131
 */
public final class CoastalPiracy extends CardImpl {

    public CoastalPiracy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}{U}");

        // Whenever a creature you control deals combat damage to an opponent, you may draw a card.
        this.addAbility(new DealsDamageToAPlayerAllTriggeredAbility(
                Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1),
                StaticFilters.FILTER_CONTROLLED_A_CREATURE, true, SetTargetPointer.PLAYER,
                true, true, TargetController.OPPONENT
        ));
    }

    private CoastalPiracy(final CoastalPiracy card) {
        super(card);
    }

    @Override
    public CoastalPiracy copy() {
        return new CoastalPiracy(this);
    }
}
