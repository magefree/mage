
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.SacrificeEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;
/**
 *
 * @author fireshoes
 */
public final class PhyrexianNegator extends CardImpl {

    public PhyrexianNegator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}");
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.HORROR);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Trample
        this.addAbility(TrampleAbility.getInstance());
        // Whenever Phyrexian Negator is dealt damage, sacrifice that many permanents.
        this.addAbility(new PhyrexianNegatorTriggeredAbility());
    }

    private PhyrexianNegator(final PhyrexianNegator card) {
        super(card);
    }

    @Override
    public PhyrexianNegator copy() {
        return new PhyrexianNegator(this);
    }
}

class PhyrexianNegatorTriggeredAbility extends TriggeredAbilityImpl {
    PhyrexianNegatorTriggeredAbility() {
        super(Zone.BATTLEFIELD, new SacrificeEffect(new FilterPermanent(), 0,""));
    }

    private PhyrexianNegatorTriggeredAbility(final PhyrexianNegatorTriggeredAbility ability) {
        super(ability);
    }
    
    @Override
    public PhyrexianNegatorTriggeredAbility copy() {
        return new PhyrexianNegatorTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PERMANENT;
    }
    
    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getTargetId().equals(this.sourceId)) {
            UUID controller = game.getControllerId(event.getTargetId());
            if (controller != null) {
                Player player = game.getPlayer(controller);
                if (player != null) {
                    getEffects().get(0).setTargetPointer(new FixedTarget(player.getId()));
                    ((SacrificeEffect) getEffects().get(0)).setAmount(StaticValue.get(event.getAmount()));
                    return true;
                }
            }
        }
        return false;
    }
    
    @Override
    public String getRule() {
        return "Whenever {this} is dealt damage, sacrifice that many permanents.";
    }
}