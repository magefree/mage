
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.BandingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author jeffwadsworth & L_J
 */
public final class MishrasWarMachine extends CardImpl {
    
    public MishrasWarMachine(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{7}");        
        this.subtype.add(SubType.JUGGERNAUT);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Banding
        this.addAbility(BandingAbility.getInstance());

        // At the beginning of your upkeep, Mishra's War Machine deals 3 damage to you unless you discard a card. If Mishra's War Machine deals damage to you this way, tap it.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new MishrasWarMachineEffect(), TargetController.YOU, false));
        
    }
    
    public MishrasWarMachine(final MishrasWarMachine card) {
        super(card);
    }
    
    @Override
    public MishrasWarMachine copy() {
        return new MishrasWarMachine(this);
    }
}

class MishrasWarMachineEffect extends OneShotEffect {
    
    public MishrasWarMachineEffect() {
        super(Outcome.Sacrifice);
        staticText = "{this} deals 3 damage to you unless you discard a card. If Mishra's War Machine deals damage to you this way, tap it";
    }
    
    public MishrasWarMachineEffect(final MishrasWarMachineEffect effect) {
        super(effect);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (controller != null
                && sourcePermanent != null) {
            DiscardCardCost cost = new DiscardCardCost();
            if (controller.chooseUse(Outcome.Benefit, "Do you wish to discard a card to prevent the 3 damage to you?", source, game)
                    && cost.canPay(source, source.getSourceId(), source.getControllerId(), game)
                    && cost.pay(source, game, source.getSourceId(), source.getControllerId(), true)) {
                return true;
            }
            if (controller.damage(3, sourcePermanent.getId(), game, false, true) > 0) {
                sourcePermanent.tap(game);
                return true;
            }
        }
        return false;
    }
    
    @Override
    public MishrasWarMachineEffect copy() {
        return new MishrasWarMachineEffect(this);
    }
}
