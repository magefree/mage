
package mage.cards.u;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author spjspj
 */
public final class UncageTheMenagerie extends CardImpl {

    public UncageTheMenagerie(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{G}{G}");

        // Search your library for up to X creature cards with different names that each have converted mana cost X, reveal them, put them into your hand, then shuffle your library.
        this.getSpellAbility().addEffect(new UncageTheMenagerieEffect());
    }

    public UncageTheMenagerie(final UncageTheMenagerie card) {
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
        filter.add(new CardTypePredicate(CardType.CREATURE));
    }

    public UncageTheMenagerieEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Search your library for up to X creature cards with different names that each have converted mana cost X, reveal them, put them into your hand, then shuffle your library.";
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
        Player player = game.getPlayer(source.getControllerId());
        Card sourceCard = game.getCard(source.getSourceId());
        if (player == null || sourceCard == null) {
            return false;
        }

        int xValue = source.getManaCostsToPay().getX();

        UncageTheMenagerieTarget target = new UncageTheMenagerieTarget(xValue);
        if (player.searchLibrary(target, source, game)) {
            if (!target.getTargets().isEmpty()) {
                Cards cards = new CardsImpl();
                for (UUID cardId : target.getTargets()) {
                    Card card = player.getLibrary().remove(cardId, game);
                    if (card != null) {
                        cards.add(card);
                    }
                }
                player.revealCards(sourceCard.getIdName(), cards, game);
                player.moveCards(cards, Zone.HAND, source, game);
            }
            player.shuffleLibrary(source, game);
            return true;
        }
        player.shuffleLibrary(source, game);
        return false;
    }
}

class UncageTheMenagerieTarget extends TargetCardInLibrary {

    private int xValue;

    public UncageTheMenagerieTarget(int xValue) {
        super(0, xValue, new FilterCreatureCard(xValue + " creature cards with different names with converted mana cost " + xValue));
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
    public boolean canTarget(UUID id, Cards cards, Game game) {
        Card card = cards.get(id, game);
        if (card != null) {
            for (UUID targetId : this.getTargets()) {
                Card iCard = game.getCard(targetId);
                if (iCard != null && iCard.getName().equals(card.getName())) {
                    return false;
                }
            }
            if (!(card.isCreature() && card.getConvertedManaCost() == xValue)) {
                return false;
            }

            return filter.match(card, game);
        }
        return false;
    }
}
