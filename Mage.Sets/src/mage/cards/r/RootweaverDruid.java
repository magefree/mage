package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RootweaverDruid extends CardImpl {

    public RootweaverDruid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // When Rootweaver Druid enters the battlefield, each opponent may search their library for up to three basic land cards. They each put one of those cards onto the battlefield tapped under your control and the rest onto the battlefield tapped under their control. Then each player who searched their library this way shuffles it.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new RootweaverDruidEffect()));
    }

    private RootweaverDruid(final RootweaverDruid card) {
        super(card);
    }

    @Override
    public RootweaverDruid copy() {
        return new RootweaverDruid(this);
    }
}

class RootweaverDruidEffect extends OneShotEffect {

    RootweaverDruidEffect() {
        super(Outcome.Benefit);
        staticText = "each opponent may search their library for up to three basic land cards. " +
                "They each put one of those cards onto the battlefield tapped under your control " +
                "and the rest onto the battlefield tapped under their control. " +
                "Then each player who searched their library this way shuffles";
    }

    private RootweaverDruidEffect(final RootweaverDruidEffect effect) {
        super(effect);
    }

    @Override
    public RootweaverDruidEffect copy() {
        return new RootweaverDruidEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Cards mine = new CardsImpl();
        Cards theirs = new CardsImpl();
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        for (UUID playerId : game.getOpponents(source.getControllerId())) {
            Player player = game.getPlayer(playerId);
            if (player == null || !player.chooseUse(outcome, "Search your library?", source, game)) {
                continue;
            }
            TargetCardInLibrary target = new TargetCardInLibrary(
                    0, 3, StaticFilters.FILTER_CARD_BASIC_LAND
            );
            player.searchLibrary(target, source, game);
            player.shuffleLibrary(source, game);
            Cards cards = new CardsImpl(target.getTargets());
            if (cards.isEmpty()) {
                continue;
            }
            target = new TargetCardInLibrary();
            player.choose(outcome, cards, target, source, game);
            mine.addAll(target.getTargets());
            cards.removeAll(target.getTargets());
            theirs.addAll(cards);
        }
        controller.moveCards(
                mine.getCards(game), Zone.BATTLEFIELD, source, game,
                true, false, false, null
        );
        controller.moveCards(
                theirs.getCards(game), Zone.BATTLEFIELD, source, game,
                true, false, true, null
        );
        return true;
    }
}
