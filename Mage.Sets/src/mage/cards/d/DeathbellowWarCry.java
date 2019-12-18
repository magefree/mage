package mage.cards.d;

import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DeathbellowWarCry extends CardImpl {

    public DeathbellowWarCry(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{5}{R}{R}{R}");

        // Search your library for up to four Minotaur creature cards with different names, put them onto the battlefield, then shuffle your library.
        this.getSpellAbility().addEffect(new SearchLibraryPutInPlayEffect(new DeathbellowWarCryTarget()));
    }

    private DeathbellowWarCry(final DeathbellowWarCry card) {
        super(card);
    }

    @Override
    public DeathbellowWarCry copy() {
        return new DeathbellowWarCry(this);
    }
}

class DeathbellowWarCryTarget extends TargetCardInLibrary {

    private static final FilterCard filter
            = new FilterCreatureCard("Minotaur creature cards with different names");

    static {
        filter.add(new SubtypePredicate(SubType.MINOTAUR));
    }

    DeathbellowWarCryTarget() {
        super(0, 4, filter);
    }

    private DeathbellowWarCryTarget(final DeathbellowWarCryTarget target) {
        super(target);
    }

    @Override
    public DeathbellowWarCryTarget copy() {
        return new DeathbellowWarCryTarget(this);
    }

    @Override
    public boolean canTarget(UUID id, Cards cards, Game game) {
        Card card = cards.get(id, game);
        return card != null
                && filter.match(card, game)
                && this
                .getTargets()
                .stream()
                .map(game::getCard)
                .map(Card::getName)
                .noneMatch(card.getName()::equals);
    }
}
