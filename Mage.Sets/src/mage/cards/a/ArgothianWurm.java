
package mage.cards.a;

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
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.common.FilterControlledLandPermanent;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author fireshoes
 */
public final class ArgothianWurm extends CardImpl {

    public ArgothianWurm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}");
        this.subtype.add(SubType.WURM);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Trample
        this.addAbility(TrampleAbility.getInstance());
        
        // When Argothian Wurm enters the battlefield, any player may sacrifice a land. If a player does, put Argothian Wurm on top of its owner's library.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ArgothianWurmEffect(), false));
    }

    private ArgothianWurm(final ArgothianWurm card) {
        super(card);
    }

    @Override
    public ArgothianWurm copy() {
        return new ArgothianWurm(this);
    }
}

class ArgothianWurmEffect extends PutOnLibrarySourceEffect {
        
    ArgothianWurmEffect() {
        super(true);
        this.staticText = "any player may sacrifice a land. If a player does, put {this} on top of its owner's library";
    }
    
    private ArgothianWurmEffect(final ArgothianWurmEffect effect) {
        super(effect);
    }
    
    @Override
    public ArgothianWurmEffect copy() {
        return new ArgothianWurmEffect(this);
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