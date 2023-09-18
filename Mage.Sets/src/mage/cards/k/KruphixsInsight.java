package mage.cards.k;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.RevealLibraryPickControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.filter.FilterCard;

/**
 *
 * @author LevelX2
 */
public final class KruphixsInsight extends CardImpl {

    private static final FilterCard filter = new FilterCard("enchantment cards");

    static {
        filter.add(CardType.ENCHANTMENT.getPredicate());
    }

    public KruphixsInsight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{G}");

        // Reveal the top six cards of your library. Put up to three enchantment cards from among them into your hand
        // and the rest of the revealed cards into your graveyard.
        Effect effect = new RevealLibraryPickControllerEffect(6, 3, filter, PutCards.HAND, PutCards.GRAVEYARD, false);
        effect.setText("reveal the top six cards of your library. " +
                "Put up to three enchantment cards from among them into your hand " +
                "and the rest of the revealed cards into your graveyard");
        this.getSpellAbility().addEffect(effect);
    }

    private KruphixsInsight(final KruphixsInsight card) {
        super(card);
    }

    @Override
    public KruphixsInsight copy() {
        return new KruphixsInsight(this);
    }
}
