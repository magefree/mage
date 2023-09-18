package mage.cards.n;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.*;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInLibrary;

import java.util.Objects;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NivMizzetReborn extends CardImpl {

    public NivMizzetReborn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}{U}{B}{R}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DRAGON);
        this.subtype.add(SubType.AVATAR);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Niv-Mizzet Reborn enters the battlefield, reveal the top ten cards of your library. For each color pair, choose a card that's exactly those colors from among them. Put the chosen cards into your hand and the rest on the bottom of your library in a random order.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new NivMizzetRebornEffect()));
    }

    private NivMizzetReborn(final NivMizzetReborn card) {
        super(card);
    }

    @Override
    public NivMizzetReborn copy() {
        return new NivMizzetReborn(this);
    }
}

class NivMizzetRebornEffect extends OneShotEffect {

    NivMizzetRebornEffect() {
        super(Outcome.Benefit);
        staticText = "reveal the top ten cards of your library. For each color pair, "
                + "choose a card that's exactly those colors from among them. "
                + "Put the chosen cards into your hand and the rest on the bottom of your library in a random order.";
    }

    private NivMizzetRebornEffect(final NivMizzetRebornEffect effect) {
        super(effect);
    }

    @Override
    public NivMizzetRebornEffect copy() {
        return new NivMizzetRebornEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl(player.getLibrary().getTopCards(game, 10));
        if (cards.isEmpty()) {
            return false;
        }
        player.revealCards(source, cards, game);
        TargetCard target = new NivMizzetRebornTarget();
        player.choose(outcome, cards, target, source, game);
        Cards toHand = new CardsImpl(target.getTargets());
        player.moveCardsToHandWithInfo(toHand, source, game, true);
        cards.retainZone(Zone.LIBRARY, game);
        player.putCardsOnBottomOfLibrary(cards, game, source, false);
        return true;
    }
}

class NivMizzetRebornTarget extends TargetCardInLibrary {

    private static enum NivMizzetRebornPredicate implements Predicate<Card> {
        instance;

        @Override
        public boolean apply(Card input, Game game) {
            return input.getColor(game).getColorCount() == 2;
        }
    }

    private static final FilterCard filter
            = new FilterCard("a card of each color pair");

    static {
        filter.add(NivMizzetRebornPredicate.instance);
    }

    NivMizzetRebornTarget() {
        super(0, 10, filter);
    }

    private NivMizzetRebornTarget(final NivMizzetRebornTarget target) {
        super(target);
    }

    @Override
    public NivMizzetRebornTarget copy() {
        return new NivMizzetRebornTarget(this);
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
        return this.getTargets().isEmpty()
                || this
                .getTargets()
                .stream()
                .map(game::getCard)
                .filter(Objects::nonNull)
                .map(c -> c.getColor(game))
                .noneMatch(card.getColor(game)::equals);
    }
}
