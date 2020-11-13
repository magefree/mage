package mage.cards.j;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.target.common.TargetCardInLibrary;

import java.util.Objects;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class JourneyForTheElixir extends CardImpl {

    public JourneyForTheElixir(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{G}");

        // Search your library and graveyard for a basic land card and a card named Jiang Yanggu, reveal them, put them into your hand, then shuffle your library.
        this.getSpellAbility().addEffect(new SearchLibraryPutInHandEffect(new JourneyForTheElixirTarget()));
    }

    public JourneyForTheElixir(final JourneyForTheElixir card) {
        super(card);
    }

    @Override
    public JourneyForTheElixir copy() {
        return new JourneyForTheElixir(this);
    }
}

class JourneyForTheElixirTarget extends TargetCardInLibrary {

    private static final String name = "Jiang Yanggu";
    private static final FilterCard filter
            = new FilterCard("a basic land card and a card named Jiang Yanggu");

    static {
        filter.add(Predicates.or(
                Predicates.and(
                        SuperType.BASIC.getPredicate(),
                        CardType.LAND.getPredicate()
                ),
                new NamePredicate(name)
        ));
    }

    JourneyForTheElixirTarget() {
        super(0, 2, filter);
    }

    private JourneyForTheElixirTarget(final JourneyForTheElixirTarget target) {
        super(target);
    }

    @Override
    public JourneyForTheElixirTarget copy() {
        return new JourneyForTheElixirTarget(this);
    }

    @Override
    public boolean canTarget(UUID playerId, UUID id, Ability source, Game game) {
        if (!super.canTarget(playerId, id, source, game)) {
            return false;
        }
        Card card = game.getCard(id);
        if (card == null) {
            return false;
        }
        if (this.getTargets().isEmpty()) {
            return true;
        }
        Cards cards = new CardsImpl(this.getTargets());
        if (card.isBasic()
                && card.isLand()
                && cards
                .getCards(game)
                .stream()
                .filter(Objects::nonNull)
                .filter(MageObject::isBasic)
                .anyMatch(MageObject::isLand)) {
            return false;
        }
        if (name.equals(card.getName())
                && cards
                .getCards(game)
                .stream()
                .map(MageObject::getName)
                .anyMatch(name::equals)) {
            return false;
        }
        return true;
    }
}
