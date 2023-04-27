

package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author Loki
 */
public final class Sangromancer extends CardImpl {

    public Sangromancer (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}{B}");
        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);
        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(new SangromancerFirstTriggeredAbility());
        this.addAbility(new SangromancerSecondTriggeredAbility());
    }

    public Sangromancer (final Sangromancer card) {
        super(card);
    }

    @Override
    public Sangromancer copy() {
        return new Sangromancer(this);
    }
}

class SangromancerFirstTriggeredAbility extends TriggeredAbilityImpl {
    SangromancerFirstTriggeredAbility() {
        super(Zone.BATTLEFIELD, new GainLifeEffect(3), true);
        setTriggerPhrase("Whenever a creature an opponent controls dies, ");
    }

    SangromancerFirstTriggeredAbility(final SangromancerFirstTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public SangromancerFirstTriggeredAbility copy() {
        return new SangromancerFirstTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (((ZoneChangeEvent) event).isDiesEvent()) {
            Permanent p = (Permanent) game.getLastKnownInformation(event.getTargetId(), Zone.BATTLEFIELD);
            if (p != null && p.isCreature(game) && game.getOpponents(this.getControllerId()).contains(p.getControllerId())) {
                return true;
            }
        }
        return false;
    }
}

class SangromancerSecondTriggeredAbility extends TriggeredAbilityImpl {
    SangromancerSecondTriggeredAbility() {
        super(Zone.BATTLEFIELD, new GainLifeEffect(3), true);
        setTriggerPhrase("Whenever an opponent discards a card, ");
    }

    SangromancerSecondTriggeredAbility(final SangromancerSecondTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public SangromancerSecondTriggeredAbility copy() {
        return new SangromancerSecondTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DISCARDED_CARD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return game.getOpponents(this.getControllerId()).contains(event.getPlayerId());
    }
}