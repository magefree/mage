package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.ColorAssignment;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorlessPredicate;
import mage.game.Game;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Conflux extends CardImpl {

    public Conflux(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{W}{U}{B}{R}{G}");

        // Search your library for a white card, a blue card, a black card, a red card, and a green card. Reveal those cards and put them into your hand. Then shuffle your library.
        this.getSpellAbility().addEffect(new SearchLibraryPutInHandEffect(
                new ConfluxTarget(), true
        ).setText("search your library for a white card, a blue card, a black card, a red card, and a green card. " +
                "Reveal those cards, put them into your hand, then shuffle"));
    }

    private Conflux(final Conflux card) {
        super(card);
    }

    @Override
    public Conflux copy() {
        return new Conflux(this);
    }
}

class ConfluxTarget extends TargetCardInLibrary {

    private static final FilterCard filter
            = new FilterCard("a white card, a blue card, a black card, a red card, and a green card");

    static {
        filter.add(Predicates.not(ColorlessPredicate.instance));
    }

    private static final ColorAssignment colorAssigner = new ColorAssignment("W", "U", "B", "R", "G");

    ConfluxTarget() {
        super(0, 5, filter);
    }

    private ConfluxTarget(final ConfluxTarget target) {
        super(target);
    }

    @Override
    public ConfluxTarget copy() {
        return new ConfluxTarget(this);
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
        cards.add(card);
        return colorAssigner.getRoleCount(cards, game) >= cards.size();
    }
}
