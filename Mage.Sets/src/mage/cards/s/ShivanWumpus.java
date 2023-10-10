
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.PutOnLibrarySourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.filter.common.FilterControlledLandPermanent;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author LevelX2
 */
public final class ShivanWumpus extends CardImpl {

    public ShivanWumpus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}");
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Trample
        this.addAbility(TrampleAbility.getInstance());
        
        // When Shivan Wumpus enters the battlefield, any player may sacrifice a land. If a player does, put Shivan Wumpus on top of its owner's library.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ShivanWumpusEffect(), false));
    }

    private ShivanWumpus(final ShivanWumpus card) {
        super(card);
    }

    @Override
    public ShivanWumpus copy() {
        return new ShivanWumpus(this);
    }
}

class ShivanWumpusEffect extends PutOnLibrarySourceEffect {
        
    ShivanWumpusEffect() {
        super(true);
        this.staticText = "any player may sacrifice a land. If a player does, put {this} on top of its owner's library";
    }
    
    private ShivanWumpusEffect(final ShivanWumpusEffect effect) {
        super(effect);
    }
    
    @Override
    public ShivanWumpusEffect copy() {
        return new ShivanWumpusEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            boolean costPaid = false;
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Cost cost = new SacrificeTargetCost(new TargetControlledPermanent(new FilterControlledLandPermanent()));
                Player player = game.getPlayer(playerId);
                if (player != null
                        && cost.canPay(source, source, playerId, game)
                        && player.chooseUse(Outcome.Sacrifice, "Sacrifice a land?", source, game)
                        && cost.pay(source, game, source, playerId, true, null)) {
                    costPaid = true;
                }
            }
            if (costPaid) {
                super.apply(game, source);
            }
            return true;
        }
        return false;
    }
}
