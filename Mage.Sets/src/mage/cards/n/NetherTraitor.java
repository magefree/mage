
package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.mana.ColoredManaCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToBattlefieldEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.ShadowAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.ColoredManaSymbol;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;

/**
 *
 * @author emerald000
 */
public final class NetherTraitor extends CardImpl {

    public NetherTraitor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{B}{B}");
        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Haste
        this.addAbility(HasteAbility.getInstance());
        
        // Shadow
        this.addAbility(ShadowAbility.getInstance());
        
        // Whenever another creature is put into your graveyard from the battlefield, you may pay {B}. If you do, return Nether Traitor from your graveyard to the battlefield.
        this.addAbility(new NetherTraitorTriggeredAbility());
    }

    private NetherTraitor(final NetherTraitor card) {
        super(card);
    }

    @Override
    public NetherTraitor copy() {
        return new NetherTraitor(this);
    }
}

class NetherTraitorTriggeredAbility extends TriggeredAbilityImpl {
    
    NetherTraitorTriggeredAbility(){
        super(Zone.GRAVEYARD, new DoIfCostPaid(new ReturnSourceFromGraveyardToBattlefieldEffect(), new ColoredManaCost(ColoredManaSymbol.B)));
    }
    
    private NetherTraitorTriggeredAbility(final NetherTraitorTriggeredAbility ability) {
        super(ability);
    }
    
    @Override
    public NetherTraitorTriggeredAbility copy(){
        return new NetherTraitorTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }
    
    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        for (Zone z : Zone.values()) {
            if (game.getShortLivingLKI(sourceId, z) && z != Zone.GRAVEYARD) {
                return false;
            }
        }
        if (zEvent.isDiesEvent()) {
            if (zEvent.getTarget() != null &&
                    zEvent.getTarget().isOwnedBy(this.getControllerId()) &&
                    zEvent.getTarget().isCreature(game)&&
                    !zEvent.getTarget().getId().equals(this.getSourceId())) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public String getRule() {
        return "Whenever another creature is put into your graveyard from the battlefield, you may pay {B}. If you do, return {this} from your graveyard to the battlefield.";
    }
}
