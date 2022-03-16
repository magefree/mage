package mage.cards.u;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.target.common.TargetCardInLibrary;
import mage.util.CardUtil;

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

    private static final FilterCard filter = new FilterCard("creature");

    static {
        filter.add(CardType.CREATURE.getPredicate());
    }

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
        return new SearchLibraryPutInHandEffect(new UncageTheMenagerieTarget(
                source.getManaCostsToPay().getX()), true, true
        ).apply(game, source);
    }
}

class UncageTheMenagerieTarget extends TargetCardInLibrary {

    private final int xValue;

    public UncageTheMenagerieTarget(int xValue) {
        super(0, xValue, new FilterCreatureCard(xValue + " creature cards with different names with mana value " + xValue));
        this.xValue = xValue;
    }

    public UncageTheMenagerieTarget(final UncageTheMenagerieTarget target) {
        super(target);
        this.xValue = target.xValue;
    }

    @Override
    public UncageTheMenagerieTarget copy() {
        return new UncageTheMenagerieTarget(this);
    }

    @Override
    public boolean canTarget(UUID playerId, UUID id, Ability source, Cards cards, Game game) {
        if (!super.canTarget(playerId, id, source, cards, game)) {
            return false;
        }
        Card card = cards.get(id, game);
        return card.getManaValue() == xValue
                && this
                .getTargets()
                .stream()
                .map(game::getCard)
                .noneMatch(c -> CardUtil.haveSameNames(c, card));
    }
}
