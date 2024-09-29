
package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToHandEffect;
import mage.abilities.keyword.ShroudAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 *
 * @author Plopman
 */
public final class Gigapede extends CardImpl {

    public Gigapede(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}{G}");
        this.subtype.add(SubType.INSECT);

        this.power = new MageInt(6);
        this.toughness = new MageInt(1);

        // Shroud
        this.addAbility(ShroudAbility.getInstance());
        // At the beginning of your upkeep, if Gigapede is in your graveyard, you may discard a card. If you do, return Gigapede to your hand.
        this.addAbility(new GigapedeTriggerdAbility());
    }

    private Gigapede(final Gigapede card) {
        super(card);
    }

    @Override
    public Gigapede copy() {
        return new Gigapede(this);
    }
}


class GigapedeTriggerdAbility extends BeginningOfUpkeepTriggeredAbility {

    GigapedeTriggerdAbility() {
        super(Zone.GRAVEYARD, new DoIfCostPaid(new ReturnSourceFromGraveyardToHandEffect(), new DiscardCardCost()), TargetController.YOU, false);
    }

    private GigapedeTriggerdAbility(final GigapedeTriggerdAbility ability) {
        super(ability);
    }

    @Override
    public GigapedeTriggerdAbility copy() {
        return new GigapedeTriggerdAbility(this);
    }
    

    @Override
    public boolean checkInterveningIfClause(Game game) {
        Player controller = game.getPlayer(controllerId);
        if(controller != null && controller.getGraveyard().contains(sourceId)){
            return super.checkInterveningIfClause(game);
        }
        return false;
    }

    @Override
    public String getRule() {
        return "At the beginning of your upkeep, if {this} is in your graveyard, you may discard a card. If you do, return {this} to your hand";
    }
    
}
