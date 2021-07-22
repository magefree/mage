package mage.cards.r;

import mage.MageItem;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

import java.util.Objects;
import java.util.UUID;

/**
 * @author LevelX2
 */
public final class RitesOfSpring extends CardImpl {

    public RitesOfSpring(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{G}");

        // Discard any number of cards. Search your library for up to that many basic land cards, reveal those cards, and put them into your hand. Then shuffle your library.
        getSpellAbility().addEffect(new RitesOfSpringEffect());
    }

    private RitesOfSpring(final RitesOfSpring card) {
        super(card);
    }

    @Override
    public RitesOfSpring copy() {
        return new RitesOfSpring(this);
    }
}

class RitesOfSpringEffect extends OneShotEffect {

    RitesOfSpringEffect() {
        super(Outcome.DrawCard);
        this.staticText = "discard any number of cards. Search your library for up to that many basic land cards, "
                + "reveal them, put them into your hand, then shuffle";
    }

    private RitesOfSpringEffect(final RitesOfSpringEffect effect) {
        super(effect);
    }

    @Override
    public RitesOfSpringEffect copy() {
        return new RitesOfSpringEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }

        int numDiscarded = controller.discard(0, Integer.MAX_VALUE, false, source, game).size();

        if (numDiscarded == 0) {
            return true;
        }

        TargetCardInLibrary target = new TargetCardInLibrary(
                numDiscarded, StaticFilters.FILTER_CARD_BASIC_LAND
        );
        controller.searchLibrary(target, source, game);
        Cards cards = new CardsImpl();
        controller
                .getLibrary()
                .getCards(game)
                .stream()
                .filter(Objects::nonNull)
                .map(MageItem::getId)
                .filter(target.getTargets()::contains)
                .forEach(cards::add);
        controller.revealCards(source, cards, game);
        controller.moveCards(cards, Zone.HAND, source, game);
        controller.shuffleLibrary(source, game);
        return true;
    }
}
