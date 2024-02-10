package mage.cards.r;

import java.util.UUID;

import mage.abilities.effects.common.ExileTargetCardCopyAndCastEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterNonlandCard;
import mage.filter.predicate.card.PutIntoGraveFromAnywhereThisTurnPredicate;
import mage.target.common.TargetCardInGraveyard;
import mage.watchers.common.CardsPutIntoGraveyardWatcher;

/**
 *
 * @author DominionSpy
 */
public final class ReenactTheCrime extends CardImpl {

    private static final FilterNonlandCard filter = new FilterNonlandCard();

    static {
        filter.add(PutIntoGraveFromAnywhereThisTurnPredicate.instance);
    }

    public ReenactTheCrime(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}{U}{U}");

        // Exile target nonland card in a graveyard that was put there from anywhere this turn.
        // Copy it. You may cast the copy without paying its mana cost.
        getSpellAbility().addEffect(new ExileTargetCardCopyAndCastEffect(true)
                .setText("Exile target nonland card in a graveyard that was put there from anywhere this turn. " +
                        "Copy it. You may cast the copy without paying its mana cost."));
        getSpellAbility().addTarget(new TargetCardInGraveyard(filter));
        getSpellAbility().addWatcher(new CardsPutIntoGraveyardWatcher());

    }

    private ReenactTheCrime(final ReenactTheCrime card) {
        super(card);
    }

    @Override
    public ReenactTheCrime copy() {
        return new ReenactTheCrime(this);
    }
}
