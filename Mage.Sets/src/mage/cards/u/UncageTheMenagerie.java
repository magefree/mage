package mage.cards.u;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.target.common.TargetCardWithDifferentNameInLibrary;

import java.util.UUID;

/**
 * @author spjspj
 */
public final class UncageTheMenagerie extends CardImpl {

    public UncageTheMenagerie(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{G}{G}");

        // Search your library for up to X creature cards with different names that each have converted mana cost X, reveal them, put them into your hand, then shuffle your library.
        this.getSpellAbility().addEffect(new UncageTheMenagerieEffect());
    }

    private UncageTheMenagerie(final UncageTheMenagerie card) {
        super(card);
    }

    @Override
    public UncageTheMenagerie copy() {
        return new UncageTheMenagerie(this);
    }
}

class UncageTheMenagerieEffect extends OneShotEffect {

    public UncageTheMenagerieEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Search your library for up to X creature cards with different names " +
                "that each have mana value X, reveal them, put them into your hand, then shuffle.";
    }

    public UncageTheMenagerieEffect(final UncageTheMenagerieEffect effect) {
        super(effect);
    }

    @Override
    public UncageTheMenagerieEffect copy() {
        return new UncageTheMenagerieEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int xValue = source.getManaCostsToPay().getX();
        FilterCard filter = new FilterCreatureCard(xValue + " creature cards with different names that each have mana value " + xValue);
        filter.add(new ManaValuePredicate(ComparisonType.EQUAL_TO, xValue));
        return new SearchLibraryPutInHandEffect(
                new TargetCardWithDifferentNameInLibrary(0, xValue, filter), true
        ).apply(game, source);
    }
}
