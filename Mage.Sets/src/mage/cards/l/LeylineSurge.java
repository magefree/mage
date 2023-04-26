package mage.cards.l;

import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
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
public final class LeylineSurge extends CardImpl {

    public LeylineSurge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "");

        this.color.setGreen(true);
        this.nightCard = true;

        // At the beginning of your upkeep, you may put a permanent card from your hand onto the battlefield.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                new PutCardFromHandOntoBattlefieldEffect(StaticFilters.FILTER_CARD_A_PERMANENT),
                TargetController.YOU, false
        ));
    }

    private LeylineSurge(final LeylineSurge card) {
        super(card);
    }

    @Override
    public LeylineSurge copy() {
        return new LeylineSurge(this);
    }
}
