package mage.cards.k;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ChooseCreatureTypeEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author Saga
 */
public final class KindredSummons extends CardImpl {

    public KindredSummons(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{5}{G}{G}");

        // Choose a creature type. Reveal cards from the top of your library until you reveal X creature cards of the chosen type,
        // where X is the number of creatures you control of that type. Put those cards onto the battlefield,
        // then shuffle the rest of the revealed cards into your library.
        this.getSpellAbility().addEffect(new ChooseCreatureTypeEffect(Outcome.PutCreatureInPlay));
        this.getSpellAbility().addEffect(new KindredSummonsEffect());
    }

    private KindredSummons(final KindredSummons card) {
        super(card);
    }

    @Override
    public KindredSummons copy() {
        return new KindredSummons(this);
    }
}

class KindredSummonsEffect extends OneShotEffect {

    public KindredSummonsEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Reveal cards from the top of your library until you reveal X creature cards of the chosen type, "
                + "where X is the number of creatures you control of that type. Put those cards onto the battlefield, "
                + "then shuffle the rest of the revealed cards into your library";
    }

    private KindredSummonsEffect(final KindredSummonsEffect effect) {
        super(effect);
    }

    @Override
    public KindredSummonsEffect copy() {
        return new KindredSummonsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            SubType subType = ChooseCreatureTypeEffect.getChosenCreatureType(source.getSourceId(), game);
            if (subType == null) {
                return false;
            }
            FilterControlledCreaturePermanent filterPermanent = new FilterControlledCreaturePermanent("creature you control of the chosen type");
            filterPermanent.add(subType.getPredicate());
            int numberOfCards = game.getBattlefield().countAll(filterPermanent, source.getControllerId(), game);
            Cards revealed = new CardsImpl();
            Set<Card> chosenSubtypeCreatureCards = new LinkedHashSet<>();
            Cards otherCards = new CardsImpl();
            FilterCreatureCard filterCard = new FilterCreatureCard("creature card of the chosen type");
            filterCard.add(subType.getPredicate());
            if (numberOfCards == 0) { // no matches so nothing is revealed
                game.informPlayers("There are 0 creature cards of the chosen type in " + controller.getName() + "'s library.");
                return true;
            }
            for (Card card : controller.getLibrary().getCards(game)) {
                revealed.add(card);
                if (card != null
                        && filterCard.match(card, game)) {
                    chosenSubtypeCreatureCards.add(card);
                    if (chosenSubtypeCreatureCards.size() == numberOfCards) {
                        break;
                    }
                } else {
                    otherCards.add(card);
                }
            }
            controller.revealCards(source, revealed, game);
            controller.moveCards(chosenSubtypeCreatureCards, Zone.BATTLEFIELD, source, game);
            if (!otherCards.isEmpty()) {
                controller.putCardsOnTopOfLibrary(otherCards, game, source, false);
                controller.shuffleLibrary(source, game);
            }
            return true;
        }
        return false;
    }
}
