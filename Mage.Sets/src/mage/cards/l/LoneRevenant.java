
package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;

/**
 *
 * @author jeffwadsworth
 */
public final class LoneRevenant extends CardImpl {

    public LoneRevenant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{U}{U}");
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        this.addAbility(HexproofAbility.getInstance());

        // Whenever Lone Revenant deals combat damage to a player, if you control no other creatures, look at the top four cards of your library. Put one of them into your hand and the rest on the bottom of your library in any order.
        this.addAbility(new LoneRevenantTriggeredAbility());
    }

    private LoneRevenant(final LoneRevenant card) {
        super(card);
    }

    @Override
    public LoneRevenant copy() {
        return new LoneRevenant(this);
    }
}

class LoneRevenantTriggeredAbility extends TriggeredAbilityImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent();

    public LoneRevenantTriggeredAbility() {
        super(Zone.BATTLEFIELD, new LookLibraryAndPickControllerEffect(StaticValue.get(4), false, StaticValue.get(1), new FilterCard(), Zone.LIBRARY, false, false));
    }

    public LoneRevenantTriggeredAbility(final LoneRevenantTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public LoneRevenantTriggeredAbility copy() {
        return new LoneRevenantTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getSourceId().equals(this.sourceId) && ((DamagedPlayerEvent) event).isCombatDamage()) {
            Permanent permanent = game.getPermanent(event.getSourceId());
            int number = game.getBattlefield().countAll(filter, controllerId, game);

            if (permanent != null && number != 1) {
                return false;
            }
            return permanent != null || number == 0;
        }
        return false;
    }

    @Override
    public String getTriggerPhrase() {
        return "Whenever {this} deals combat damage to a player, if you control no other creatures, " ;
    }

}
