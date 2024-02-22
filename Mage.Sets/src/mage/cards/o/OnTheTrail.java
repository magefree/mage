package mage.cards.o;

import mage.abilities.common.DrawNthCardTriggeredAbility;
import mage.abilities.effects.common.PutCardFromHandOntoBattlefieldEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class OnTheTrail extends CardImpl {

    public OnTheTrail(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{G}");

        // Whenever you draw your second card each turn, you may put a land card from your hand onto the battlefield tapped.
        this.addAbility(new DrawNthCardTriggeredAbility(
                new PutCardFromHandOntoBattlefieldEffect(
                        StaticFilters.FILTER_CARD_LAND_A, false, true
                ), false, 2
        ));
    }

    private OnTheTrail(final OnTheTrail card) {
        super(card);
    }

    @Override
    public OnTheTrail copy() {
        return new OnTheTrail(this);
    }
}
