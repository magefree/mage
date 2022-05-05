package mage.cards.w;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.HideawayAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.ExileZone;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

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
        this.addAbility(new HideawayAbility(4));
        this.addAbility(new EntersBattlefieldTappedAbility());

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
        ExileZone zone = game.getExile().getExileZone(CardUtil.getExileZoneId(game, source, -1));
        if (player == null || zone == null || zone.isEmpty()) {
            return false;
        }
        return player.moveCards(zone, Zone.HAND, source, game);
    }
}
