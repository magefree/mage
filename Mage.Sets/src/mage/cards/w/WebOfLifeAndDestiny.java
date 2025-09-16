package mage.cards.w;

import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.keyword.ConvokeAbility;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WebOfLifeAndDestiny extends CardImpl {

    public WebOfLifeAndDestiny(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{6}{G}{G}");

        // Convoke
        this.addAbility(new ConvokeAbility());

        // At the beginning of combat on your turn, look at the top five cards of your library. You may put a creature card from among them onto the battlefield. Put the rest on the bottom of your library in a random order.
        this.addAbility(new BeginningOfCombatTriggeredAbility(new LookLibraryAndPickControllerEffect(
                5, 1, StaticFilters.FILTER_CARD_CREATURE,
                PutCards.BATTLEFIELD, PutCards.BOTTOM_RANDOM
        )));
    }

    private WebOfLifeAndDestiny(final WebOfLifeAndDestiny card) {
        super(card);
    }

    @Override
    public WebOfLifeAndDestiny copy() {
        return new WebOfLifeAndDestiny(this);
    }
}
