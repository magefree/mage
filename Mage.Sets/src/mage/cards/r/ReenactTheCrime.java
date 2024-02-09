package mage.cards.r;

import java.util.UUID;

import mage.abilities.effects.common.ExileTargetCardCopyAndCastEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterNonlandCard;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.target.common.TargetCardInASingleGraveyard;
import mage.watchers.common.CardsPutIntoGraveyardWatcher;

/**
 *
 * @author DominionSpy
 */
public final class ReenactTheCrime extends CardImpl {

    private static final FilterNonlandCard filter = new FilterNonlandCard();

    static {
        filter.add(ReenactTheCrimePredicate.instance);
    }

    public ReenactTheCrime(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}{U}{U}");

        // Exile target nonland card in a graveyard that was put there from anywhere this turn.
        // Copy it. You may cast the copy without paying its mana cost.
        getSpellAbility().addEffect(new ExileTargetCardCopyAndCastEffect(true)
                .setText("Exile target nonland card in a graveyard that was put there from anywhere this turn. " +
                        "Copy it. You may cast the copy without paying its mana cost."));
        getSpellAbility().addTarget(new TargetCardInASingleGraveyard(
                1, 1, filter));
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

enum ReenactTheCrimePredicate implements Predicate<Card> {
    instance;

    @Override
    public boolean apply(Card input, Game game) {
        CardsPutIntoGraveyardWatcher watcher = game.getState().getWatcher(CardsPutIntoGraveyardWatcher.class);
        return watcher.checkCardFromAnywhere(input, game);
    }
}
