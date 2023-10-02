
package mage.cards.j;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author TheElk801
 */
public final class JungleWayfinder extends CardImpl {

    public JungleWayfinder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Jungle Wayfinder enters the battlefield, each player may search their library for a basic land card, reveal it, put it into their hand, then shuffle their library.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new JungleWayfinderEffect(), false));
    }

    private JungleWayfinder(final JungleWayfinder card) {
        super(card);
    }

    @Override
    public JungleWayfinder copy() {
        return new JungleWayfinder(this);
    }
}

class JungleWayfinderEffect extends OneShotEffect {

    public JungleWayfinderEffect() {
        super(Outcome.Benefit);
        this.staticText = "each player may search their library for a basic land card, reveal it, put it into their hand, then shuffle";
    }

    private JungleWayfinderEffect(final JungleWayfinderEffect effect) {
        super(effect);
    }

    @Override
    public JungleWayfinderEffect copy() {
        return new JungleWayfinderEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    TargetCardInLibrary target = new TargetCardInLibrary(0, 1, StaticFilters.FILTER_CARD_BASIC_LAND);
                    if (player.chooseUse(Outcome.Benefit, "Search your library for a card to put into your hand?", source, game)) {
                        player.searchLibrary(target, source, game);
                        for (UUID cardId : target.getTargets()) {
                            Card card = player.getLibrary().getCard(cardId, game);
                            if (card != null) {
                                player.revealCards(source, new CardsImpl(card), game);
                                player.moveCards(card, Zone.HAND, source, game);
                            }
                        }
                        player.shuffleLibrary(source, game);
                    }
                }
            }
            // prevent undo
            controller.resetStoredBookmark(game);
            return true;
        }
        return false;
    }
}
