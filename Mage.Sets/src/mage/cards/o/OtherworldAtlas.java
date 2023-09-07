
package mage.cards.o;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 * @author noxx
 */
public final class OtherworldAtlas extends CardImpl {

    public OtherworldAtlas(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{4}");

        // {tap}: Put a charge counter on Otherworld Atlas.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.CHARGE.createInstance()), new TapSourceCost()));

        // {tap}: Each player draws a card for each charge counter on Otherworld Atlas.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new OtherworldAtlasDrawEffect(), new TapSourceCost()));
    }

    private OtherworldAtlas(final OtherworldAtlas card) {
        super(card);
    }

    @Override
    public OtherworldAtlas copy() {
        return new OtherworldAtlas(this);
    }
}

class OtherworldAtlasDrawEffect extends OneShotEffect {

    public OtherworldAtlasDrawEffect() {
        super(Outcome.DrawCard);
        staticText = "Each player draws a card for each charge counter on {this}";
    }

    private OtherworldAtlasDrawEffect(final OtherworldAtlasDrawEffect effect) {
        super(effect);
    }

    @Override
    public OtherworldAtlasDrawEffect copy() {
        return new OtherworldAtlasDrawEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player sourcePlayer = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null && sourcePlayer != null) {
            int amount = permanent.getCounters(game).getCount(CounterType.CHARGE);
            if (amount > 0) {
                for (UUID playerId : game.getState().getPlayersInRange(sourcePlayer.getId(), game)) {
                    Player player = game.getPlayer(playerId);
                    if (player != null) {
                        player.drawCards(amount, source, game);
                    }
                }
            }
        }
        return true;
    }

}
