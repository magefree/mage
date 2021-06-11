
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
 * @author Loki
 */
public final class PhyrexianObliterator extends CardImpl {

    public PhyrexianObliterator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{B}{B}{B}{B}");
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.HORROR);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever a source deals damage to Phyrexian Obliterator, that source's controller sacrifices that many permanents.
        this.addAbility(new PhyrexianObliteratorTriggeredAbility());
    }

    private PhyrexianObliterator(final PhyrexianObliterator card) {
        super(card);
    }

    @Override
    public PhyrexianObliterator copy() {
        return new PhyrexianObliterator(this);
    }
}

class PhyrexianObliteratorTriggeredAbility extends TriggeredAbilityImpl {

    PhyrexianObliteratorTriggeredAbility() {
        super(Zone.BATTLEFIELD, new SacrificeEffect(new FilterPermanent(), 0, ""));
    }

    PhyrexianObliteratorTriggeredAbility(final PhyrexianObliteratorTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public PhyrexianObliteratorTriggeredAbility copy() {
        return new PhyrexianObliteratorTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PERMANENT;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getTargetId().equals(this.sourceId)) {
            UUID controller = game.getControllerId(event.getSourceId());
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
        return "Whenever a source deals damage to {this}, that source's controller sacrifices that many permanents";
    }
}
