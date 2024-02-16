package mage.cards.t;

import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TappingAtTheWindow extends CardImpl {

    public TappingAtTheWindow(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{G}");

        // Look at the top three cards of your library.
        // You may reveal a creature card from among them and put it into your hand.
        // Put the rest into your graveyard.
        this.getSpellAbility().addEffect(new LookLibraryAndPickControllerEffect(
                3, 1, StaticFilters.FILTER_CARD_CREATURE_A, PutCards.HAND, PutCards.GRAVEYARD));

        // Flashback {2}{G}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl<>("{2}{G}")));
    }

    private TappingAtTheWindow(final TappingAtTheWindow card) {
        super(card);
    }

    @Override
    public TappingAtTheWindow copy() {
        return new TappingAtTheWindow(this);
    }
}
