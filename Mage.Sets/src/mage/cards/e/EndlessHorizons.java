package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInExile;
import mage.target.common.TargetCardInLibrary;
import mage.util.CardUtil;

import java.util.Objects;
import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class EndlessHorizons extends CardImpl {

    public EndlessHorizons(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{W}");

        // When Endless Horizons enters the battlefield, search your library for any number of Plains cards and exile them. Then shuffle your library.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new EndlessHorizonsEffect(), false));

        // At the beginning of your upkeep, you may put a card you own exiled with Endless Horizons into your hand.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new EndlessHorizonsEffect2(), TargetController.YOU, false));
    }

    private EndlessHorizons(final EndlessHorizons card) {
        super(card);
    }

    @Override
    public EndlessHorizons copy() {
        return new EndlessHorizons(this);
    }
}

class EndlessHorizonsEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("Plains card");

    static {
        filter.add(SubType.PLAINS.getPredicate());
    }

    EndlessHorizonsEffect() {
        super(Outcome.Neutral);
        this.staticText = "search your library for any number of Plains cards, exile them, then shuffle";
    }

    private EndlessHorizonsEffect(final EndlessHorizonsEffect effect) {
        super(effect);
    }

    @Override
    public EndlessHorizonsEffect copy() {
        return new EndlessHorizonsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        TargetCardInLibrary target = new TargetCardInLibrary(0, Integer.MAX_VALUE, filter);
        player.searchLibrary(target, source, game);
        Cards cards = new CardsImpl();
        target.getTargets()
                .stream()
                .map(uuid -> player.getLibrary().getCard(uuid, game))
                .filter(Objects::nonNull)
                .forEach(cards::add);
        player.moveCardsToExile(cards.getCards(game), source, game, true, CardUtil.getExileZoneId(game, source), CardUtil.getSourceName(game, source));
        player.shuffleLibrary(source, game);
        return true;
    }
}

class EndlessHorizonsEffect2 extends OneShotEffect {

    private static final FilterCard filter = new FilterCard();

    static {
        filter.add(TargetController.YOU.getOwnerPredicate());
    }

    EndlessHorizonsEffect2() {
        super(Outcome.ReturnToHand);
        this.staticText = "you may put a card you own exiled with {this} into your hand";
    }

    private EndlessHorizonsEffect2(final EndlessHorizonsEffect2 effect) {
        super(effect);
    }

    @Override
    public EndlessHorizonsEffect2 copy() {
        return new EndlessHorizonsEffect2(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        TargetCard target = new TargetCardInExile(
                0, 1, filter, CardUtil.getExileZoneId(game, source)
        );
        target.setNotTarget(true);
        controller.choose(outcome, target, source.getSourceId(), game);
        Card card = game.getCard(target.getFirstTarget());
        return card != null && controller.moveCards(card, Zone.HAND, source, game);
    }
}
