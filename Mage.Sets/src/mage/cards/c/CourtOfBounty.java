package mage.cards.c;

import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.MonarchIsSourceControllerCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.BecomesMonarchSourceEffect;
import mage.abilities.effects.common.PutCardFromHandOntoBattlefieldEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CourtOfBounty extends CardImpl {

    public CourtOfBounty(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}{G}");

        // When Court of Bounty enters the battlefield, you become the monarch.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new BecomesMonarchSourceEffect()));

        // At the beginning of your upkeep, you may put a land card from your hand onto the battlefield. If you're the monarch, instead you may put a creature or land card from your hand onto the battlefield.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new ConditionalOneShotEffect(
                new PutCardFromHandOntoBattlefieldEffect(StaticFilters.FILTER_CARD_CREATURE_OR_LAND),
                new PutCardFromHandOntoBattlefieldEffect(StaticFilters.FILTER_CARD_LAND_A),
                MonarchIsSourceControllerCondition.instance, "you may put a land card " +
                "from your hand onto the battlefield. If you're the monarch, " +
                "instead you may put a creature or land card from your hand onto the battlefield"
        ), TargetController.YOU, false));
    }

    private CourtOfBounty(final CourtOfBounty card) {
        super(card);
    }

    @Override
    public CourtOfBounty copy() {
        return new CourtOfBounty(this);
    }
}
