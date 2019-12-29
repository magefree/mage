package mage.cards.w;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.HideawayAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.ExileZone;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;
import mage.game.permanent.Permanent;

/**
 * @author TheElk801
 */
public final class WatcherForTomorrow extends CardImpl {

    public WatcherForTomorrow(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Hideaway
        this.addAbility(new HideawayAbility("creatures"));

        // When Watcher for Tomorrow leaves the battlefield, put the exiled card into its owner's hand.
        this.addAbility(new LeavesBattlefieldTriggeredAbility(new WatcherForTomorrowEffect(), false));
    }

    private WatcherForTomorrow(final WatcherForTomorrow card) {
        super(card);
    }

    @Override
    public WatcherForTomorrow copy() {
        return new WatcherForTomorrow(this);
    }
}

class WatcherForTomorrowEffect extends OneShotEffect {

    WatcherForTomorrowEffect() {
        super(Outcome.Benefit);
        staticText = "put the exiled card into its owner's hand";
    }

    private WatcherForTomorrowEffect(final WatcherForTomorrowEffect effect) {
        super(effect);
    }

    @Override
    public WatcherForTomorrowEffect copy() {
        return new WatcherForTomorrowEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Permanent permanentLeftBattlefield = (Permanent) getValue("permanentLeftBattlefield");
        if (permanentLeftBattlefield == null) {
            return false;
        }
        ExileZone zone = game.getExile().getExileZone(CardUtil.getExileZoneId(game, source.getSourceId(), permanentLeftBattlefield.getZoneChangeCounter(game)));
        if (zone == null) {
            return false;
        }
        Cards cards = new CardsImpl(zone.getCards(game));
        return player.moveCards(cards, Zone.HAND, source, game);
    }
}
