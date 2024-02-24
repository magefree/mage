package mage.cards.p;

import mage.abilities.effects.Effect;
import mage.abilities.effects.common.search.SearchLibraryPutOneOntoBattlefieldTappedRestInHandEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class Peregrination extends CardImpl {

    public Peregrination(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{G}");

        // Search your library for up to two basic land cards, reveal those cards, and put one onto the battlefield tapped and the other into your hand. Shuffle, then scry 1.
        this.getSpellAbility().addEffect(new SearchLibraryPutOneOntoBattlefieldTappedRestInHandEffect(
                new TargetCardInLibrary(0, 2, StaticFilters.FILTER_CARD_BASIC_LANDS))
                .setText("search your library for up to two basic land cards, reveal those cards, and put one onto the battlefield tapped and the other into your hand. Shuffle"));
        Effect effect = new ScryEffect(1);
        effect.concatBy(", then");
        this.getSpellAbility().addEffect(effect);
    }

    private Peregrination(final Peregrination card) {
        super(card);
    }

    @Override
    public Peregrination copy() {
        return new Peregrination(this);
    }
}
