
package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToBattlefieldEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author Plopman
 */
public final class NetherShadow extends CardImpl {

    public NetherShadow(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{B}{B}");
        this.subtype.add(SubType.SPIRIT);


        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Haste
        this.addAbility(HasteAbility.getInstance());
        // At the beginning of your upkeep, if Nether Shadow is in your graveyard with three or more creature cards above it, you may put Nether Shadow onto the battlefield.
        this.addAbility(new NetherShadowTriggerdAbility());
    }

    private NetherShadow(final NetherShadow card) {
        super(card);
    }

    @Override
    public NetherShadow copy() {
        return new NetherShadow(this);
    }
}

class NetherShadowTriggerdAbility extends BeginningOfUpkeepTriggeredAbility{
  
    
    public NetherShadowTriggerdAbility(){
        super(Zone.GRAVEYARD, new ReturnSourceFromGraveyardToBattlefieldEffect(), TargetController.YOU, true);
    }

    private NetherShadowTriggerdAbility(final NetherShadowTriggerdAbility effect) {
        super(effect);
    }    
    
    @Override
    public boolean checkInterveningIfClause(Game game) {
        Player controller = game.getPlayer(controllerId);
        if(controller != null){
            int count = -1;
            for(UUID uuid : controller.getGraveyard()){
                if(count == -1){
                    if(uuid.equals(sourceId)){
                        count = 0;
                    }
                }
                else{
                    Card card = game.getCard(uuid);
                    if(card != null && card.isCreature(game)){
                        count++;
                    }
                }
            }
            return count >= 3;
        }
        return false;
    }

    @Override
    public NetherShadowTriggerdAbility copy() {
        return new NetherShadowTriggerdAbility(this);
    }

    @Override
    public String getRule() {
        return "At the beginning of your upkeep, if {this} is in your graveyard with three or more creature cards above it, you may put {this} onto the battlefield.";
    }
    
    
    
}
