package mage.cards.b;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetOpponent;
import mage.util.CardUtil;

/**
 *
 * @author weirddan455
 */
public final class BurningRuneDemon extends CardImpl {

    public BurningRuneDemon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}{B}");

        this.subtype.add(SubType.DEMON);
        this.subtype.add(SubType.BERSERKER);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Burning-Rune Demon enters the battlefield, you may search your library for exactly two cards
        // not named Burning-Rune Demon that have different names. If you do, reveal those cards.
        // An opponent chooses one of them.
        // Put the chosen card into your hand and the other into your graveyard, then shuffle your library.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new BurningRuneDemonEffect(), true));
    }

    private BurningRuneDemon(final BurningRuneDemon card) {
        super(card);
    }

    @Override
    public BurningRuneDemon copy() {
        return new BurningRuneDemon(this);
    }
}

class BurningRuneDemonEffect extends OneShotEffect {

    public BurningRuneDemonEffect() {
        super(Outcome.Benefit);
        staticText = "search your library for exactly two cards "
                + "not named Burning-Rune Demon that have different names. If you do, reveal those cards. "
                + "An opponent chooses one of them. "
                + "Put the chosen card into your hand and the other into your graveyard, then shuffle";
    }

    private BurningRuneDemonEffect(final BurningRuneDemonEffect effect) {
        super(effect);
    }

    @Override
    public BurningRuneDemonEffect copy() {
        return new BurningRuneDemonEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            TargetCardInLibrary targetCardInLibrary = new BurningRuneDemonTarget();
            if (controller.searchLibrary(targetCardInLibrary, source, game)) {
                Cards cards = new CardsImpl(targetCardInLibrary.getTargets());
                if (!cards.isEmpty()) {
                    Player opponent;
                    Set<UUID> opponents = game.getOpponents(controller.getId());
                    if (opponents.size() == 1) {
                        opponent = game.getPlayer(opponents.iterator().next());
                    } else {
                        TargetOpponent targetOpponent = new TargetOpponent(true);
                        controller.chooseTarget(Outcome.Detriment, targetOpponent, source, game);
                        opponent = game.getPlayer(targetOpponent.getFirstTarget());
                    }
                    if (opponent != null) {
                        TargetCard targetCard = new TargetCard(Zone.LIBRARY, StaticFilters.FILTER_CARD);
                        targetCard.withChooseHint("Card to go to opponent's hand (other goes to graveyard)");
                        opponent.chooseTarget(outcome, cards, targetCard, source, game);
                        Card cardToHand = game.getCard(targetCard.getFirstTarget());
                        if (cardToHand != null) {
                            controller.moveCards(cardToHand, Zone.HAND, source, game);
                            cards.remove(cardToHand);
                        }
                        if (!cards.isEmpty()) {
                            controller.moveCards(cards, Zone.GRAVEYARD, source, game);
                        }
                    }
                }
                controller.shuffleLibrary(source, game);
                return true;
            }
        }
        return false;
    }
}

class BurningRuneDemonTarget extends TargetCardInLibrary {

    private static final FilterCard filter
            = new FilterCard("cards not named Burning-Rune Demon that have different names");

    static {
        filter.add(Predicates.not(new NamePredicate("Burning-Rune Demon")));
    }

    public BurningRuneDemonTarget() {
        super(2, filter);
    }

    private BurningRuneDemonTarget(final BurningRuneDemonTarget target) {
        super(target);
    }

    @Override
    public BurningRuneDemonTarget copy() {
        return new BurningRuneDemonTarget(this);
    }

    @Override
    public boolean canTarget(UUID playerId, UUID id, Ability source, Game game) {
        if (!super.canTarget(playerId, id, source, game)) {
            return false;
        }
        Card card = game.getCard(id);
        return card != null
                && this.getTargets()
                .stream()
                .map(game::getCard)
                .filter(Objects::nonNull)
                .map(Card::getName)
                .noneMatch(n -> CardUtil.haveSameNames(card, n, game));
    }
}
