
package mage.cards.t;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author jeffwadsworth
 */
public final class TouchOfTheEternal extends CardImpl {

    public TouchOfTheEternal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{5}{W}{W}");


        // At the beginning of your upkeep, count the number of permanents you control. Your life total becomes that number.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new TouchOfTheEternalEffect(), TargetController.YOU, false));
    }

    private TouchOfTheEternal(final TouchOfTheEternal card) {
        super(card);
    }

    @Override
    public TouchOfTheEternal copy() {
        return new TouchOfTheEternal(this);
    }
}

class TouchOfTheEternalEffect extends OneShotEffect {

    public TouchOfTheEternalEffect() {
        super(Outcome.Neutral);
        this.staticText = "count the number of permanents you control. Your life total becomes that number";
    }

    private TouchOfTheEternalEffect(final TouchOfTheEternalEffect effect) {
        super(effect);
    }

    @Override
    public TouchOfTheEternalEffect copy() {
        return new TouchOfTheEternalEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        FilterControlledPermanent filter = new FilterControlledPermanent();
        Player player = game.getPlayer(source.getControllerId());
        int permanentsInPlay = game.getBattlefield().countAll(filter, source.getControllerId(), game);
        if (player != null) {
            player.setLife(permanentsInPlay, game, source);
            return true;
        }
        return false;
    }
}
