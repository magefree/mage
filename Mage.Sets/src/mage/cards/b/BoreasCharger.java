package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.FilterPlayer;
import mage.filter.StaticFilters;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author TheElk801
 */
public final class BoreasCharger extends CardImpl {

    public BoreasCharger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.PEGASUS);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Boreas Charger leaves the battlefield, choose an opponent who controls more lands than you. Search your library for a number of Plains cards equal to the difference and reveal them. Put one of them onto the battlefield tapped and the rest into your hand. Then shuffle your library.
        this.addAbility(new LeavesBattlefieldTriggeredAbility(
                new BoreasChargerEffect(), false
        ));
    }

    public BoreasCharger(final BoreasCharger card) {
        super(card);
    }

    @Override
    public BoreasCharger copy() {
        return new BoreasCharger(this);
    }
}

class BoreasChargerEffect extends OneShotEffect {

    private static final FilterPlayer filter
            = new FilterPlayer("opponent who controls more lands than you");
    private static final FilterCard filter2
            = new FilterCard("Plains cards");
    private static final FilterCard filter3
            = new FilterCard("a card to put onto the battlefield tapped");

    static {
        filter.add(new BoreasChargerPredicate());
        filter2.add(new SubtypePredicate(SubType.PLAINS));
    }

    public BoreasChargerEffect() {
        super(Outcome.Benefit);
        this.staticText = "choose an opponent who controls more lands than you. "
                + "Search your library for a number of Plains cards "
                + "equal to the difference and reveal them. "
                + "Put one of them onto the battlefield tapped "
                + "and the rest into your hand. Then shuffle your library";
    }

    public BoreasChargerEffect(final BoreasChargerEffect effect) {
        super(effect);
    }

    @Override
    public BoreasChargerEffect copy() {
        return new BoreasChargerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        TargetPlayer target = new TargetPlayer(1, 1, true, filter);
        controller.choose(outcome, target, source.getSourceId(), game);
        Player opponent = game.getPlayer(target.getFirstTarget());
        if (opponent == null) {
            controller.shuffleLibrary(source, game);
            return false;
        }
        int landDifference = game.getBattlefield().getAllActivePermanents(
                StaticFilters.FILTER_LANDS, opponent.getId(), game
        ).size();
        landDifference -= game.getBattlefield().getAllActivePermanents(
                StaticFilters.FILTER_LANDS, controller.getId(), game
        ).size();
        landDifference = Math.abs(landDifference);
        TargetCardInLibrary target2
                = new TargetCardInLibrary(0, landDifference, filter2);
        Cards cardsToHand = new CardsImpl();
        if (controller.searchLibrary(target2, game)) {
            for (UUID cardId : target2.getTargets()) {
                Card card = game.getCard(cardId);
                if (card != null) {
                    cardsToHand.add(card);
                }
            }
        }
        if (cardsToHand.isEmpty()) {
            controller.shuffleLibrary(source, game);
            return true;
        }
        TargetCard target3 = new TargetCard(Zone.LIBRARY, filter3);
        Card cardToBattlefield = null;
        if (controller.choose(outcome, cardsToHand, target3, game)) {
            cardToBattlefield = cardsToHand.get(target2.getFirstTarget(), game);
            cardsToHand.remove(cardToBattlefield);
        }
        if (cardToBattlefield != null) {
            controller.moveCards(
                    cardToBattlefield, Zone.BATTLEFIELD, source, game,
                    true, false, true, null
            );
        }
        controller.moveCards(cardsToHand, Zone.HAND, source, game);
        controller.shuffleLibrary(source, game);
        return true;
    }
}

class BoreasChargerPredicate implements ObjectSourcePlayerPredicate<ObjectSourcePlayer<Player>> {

    @Override
    public boolean apply(ObjectSourcePlayer<Player> input, Game game) {
        Player targetPlayer = input.getObject();
        UUID playerId = input.getPlayerId();
        if (targetPlayer == null || playerId == null) {
            return false;
        }
        if (!targetPlayer.hasOpponent(playerId, game)) {
            return false;
        }
        int countTargetPlayer = game.getBattlefield().countAll(
                StaticFilters.FILTER_LANDS, targetPlayer.getId(), game
        );
        int countController = game.getBattlefield().countAll(
                StaticFilters.FILTER_LANDS, playerId, game
        );

        return countTargetPlayer > countController;
    }
}
