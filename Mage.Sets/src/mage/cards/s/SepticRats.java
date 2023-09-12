
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.InfectAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.players.Player;

/**
 *
 * @author Loki
 */
public final class SepticRats extends CardImpl {

    public SepticRats(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}{B}");
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.RAT);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        this.addAbility(InfectAbility.getInstance());
        this.addAbility(new SepticRatsTriggeredAbility());
    }

    private SepticRats(final SepticRats card) {
        super(card);
    }

    @Override
    public SepticRats copy() {
        return new SepticRats(this);
    }
}

class SepticRatsTriggeredAbility extends TriggeredAbilityImpl {
    SepticRatsTriggeredAbility() {
        super(Zone.BATTLEFIELD, new BoostSourceEffect(1, 1, Duration.EndOfTurn));
    }

    private SepticRatsTriggeredAbility(final SepticRatsTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public SepticRatsTriggeredAbility copy() {
        return new SepticRatsTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ATTACKER_DECLARED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getSourceId().equals(this.getSourceId()) ) {
            Player target = game.getPlayer(event.getTargetId());
            if (target != null) {
                if (target.getCounters().getCount(CounterType.POISON) > 0) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever {this} attacks, if defending player is poisoned, it gets +1/+1 until end of turn.";
    }
}