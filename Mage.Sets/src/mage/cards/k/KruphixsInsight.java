package mage.cards.k;

import mage.abilities.effects.common.RevealLibraryPickControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class KruphixsInsight extends CardImpl {

    public KruphixsInsight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{G}");

        // Reveal the top six cards of your library. Put up to three enchantment cards from among them into your hand
        // and the rest of the revealed cards into your graveyard.
        this.getSpellAbility().addEffect(new RevealLibraryPickControllerEffect(
                6, 3, StaticFilters.FILTER_CARD_ENCHANTMENTS,
                PutCards.HAND, PutCards.GRAVEYARD, false
        ).setText("reveal the top six cards of your library. " +
                "Put up to three enchantment cards from among them into your hand " +
                "and the rest of the revealed cards into your graveyard"));
    }

    private KruphixsInsight(final KruphixsInsight card) {
        super(card);
    }

    @Override
    public KruphixsInsight copy() {
        return new KruphixsInsight(this);
    }
}
