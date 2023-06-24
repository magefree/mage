package mage.cards.t;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.RevealLibraryPickControllerEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.filter.StaticFilters;

/**
 *
 * @author awjackson
 */
public final class TrackersInstincts extends CardImpl {

    public TrackersInstincts(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{G}");

        // Reveal the top four cards of your library. Put a creature card from among them into your hand and the rest into your graveyard.
        this.getSpellAbility().addEffect(new RevealLibraryPickControllerEffect(
                4, 1, StaticFilters.FILTER_CARD_CREATURE_A, PutCards.HAND, PutCards.GRAVEYARD, false));

        // Flashback {2}{U}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl<>("{2}{U}")));
    }

    private TrackersInstincts(final TrackersInstincts card) {
        super(card);
    }

    @Override
    public TrackersInstincts copy() {
        return new TrackersInstincts(this);
    }
}
