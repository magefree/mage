package mage.cards.g;

import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.effects.common.LookLibraryControllerEffect.PutCards;
import mage.abilities.effects.common.ExileSpellEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.common.FilterPermanentCard;

import java.util.UUID;

/**
 * @author awjackson
 */
public final class GenesisUltimatum extends CardImpl {

    private static final FilterCard filter = new FilterPermanentCard("permanent cards");

    public GenesisUltimatum(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{G}{G}{U}{U}{U}{R}{R}");

        // Look at the top five cards of your library.
        // Put any number of permanent cards from among them onto the battlefield and the rest into your hand.
        // Exile Genesis Ultimatum.
        this.getSpellAbility().addEffect(new LookLibraryAndPickControllerEffect(
                5, Integer.MAX_VALUE, filter, PutCards.BATTLEFIELD, PutCards.HAND, false
                ).setText("look at the top five cards of your library. Put any number of permanent cards from among them onto the battlefield and the rest into your hand"));
        this.getSpellAbility().addEffect(new ExileSpellEffect());
    }

    private GenesisUltimatum(final GenesisUltimatum card) {
        super(card);
    }

    @Override
    public GenesisUltimatum copy() {
        return new GenesisUltimatum(this);
    }
}
