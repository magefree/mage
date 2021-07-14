package mage.cards.s;

import mage.abilities.common.AttacksAllTriggeredAbility;
import mage.abilities.effects.keyword.InvestigateEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SearchThePremises extends CardImpl {

    public SearchThePremises(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{W}");

        // Whenever a creature attacks you or a planeswalker you control, investigate.
        this.addAbility(new AttacksAllTriggeredAbility(
                new InvestigateEffect(), false, true
        ));
    }

    private SearchThePremises(final SearchThePremises card) {
        super(card);
    }

    @Override
    public SearchThePremises copy() {
        return new SearchThePremises(this);
    }
}
