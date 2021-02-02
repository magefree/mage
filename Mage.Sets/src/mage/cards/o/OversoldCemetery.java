package mage.cards.o;

import java.util.UUID;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.condition.common.CardsInControllerGraveyardCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInGraveyard;

/**
 *
 * @author fireshoes
 */
public final class OversoldCemetery extends CardImpl {

    public OversoldCemetery(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{B}");

        // At the beginning of your upkeep, if you have four or more creature cards in your graveyard, you may return target creature card from your graveyard to your hand.
        TriggeredAbility ability = new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new ReturnFromGraveyardToHandTargetEffect(), TargetController.YOU, true);
        ability.addTarget(new TargetCardInGraveyard(StaticFilters.FILTER_CARD_CREATURE));
        CardsInControllerGraveyardCondition condition = new CardsInControllerGraveyardCondition(4, StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD);
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(ability, condition, "At the beginning of your upkeep, if you have four or more creature cards in your graveyard, you may return target creature card from your graveyard to your hand."));
    }

    private OversoldCemetery(final OversoldCemetery card) {
        super(card);
    }

    @Override
    public OversoldCemetery copy() {
        return new OversoldCemetery(this);
    }
}
