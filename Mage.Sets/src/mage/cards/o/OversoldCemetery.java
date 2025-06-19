package mage.cards.o;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.CardsInControllerGraveyardCondition;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInGraveyard;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class OversoldCemetery extends CardImpl {

    private static final Condition condition = new CardsInControllerGraveyardCondition(4, StaticFilters.FILTER_CARD_CREATURES);

    public OversoldCemetery(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{B}");

        // At the beginning of your upkeep, if you have four or more creature cards in your graveyard, you may return target creature card from your graveyard to your hand.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(
                new ReturnFromGraveyardToHandTargetEffect(), true
        ).withInterveningIf(condition);
        ability.addTarget(new TargetCardInGraveyard(StaticFilters.FILTER_CARD_CREATURE));
        this.addAbility(ability);
    }

    private OversoldCemetery(final OversoldCemetery card) {
        super(card);
    }

    @Override
    public OversoldCemetery copy() {
        return new OversoldCemetery(this);
    }
}
