package mage.cards.f;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
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
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author North
 */
public final class FalkenrathNoble extends CardImpl {

    public FalkenrathNoble(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");
        this.subtype.add(SubType.VAMPIRE, SubType.NOBLE);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        this.addAbility(FlyingAbility.getInstance());
        // Whenever Falkenrath Noble or another creature dies, target player loses 1 life and you gain 1 life.
        this.addAbility(new FalkenrathNobleTriggeredAbility());
    }

    private FalkenrathNoble(final FalkenrathNoble card) {
        super(card);
    }

    @Override
    public FalkenrathNoble copy() {
        return new FalkenrathNoble(this);
    }
}

class FalkenrathNobleTriggeredAbility extends TriggeredAbilityImpl {

    public FalkenrathNobleTriggeredAbility() {
        super(Zone.BATTLEFIELD, new LoseLifeTargetEffect(1), false);
        this.addEffect(new GainLifeEffect(1));
        this.addTarget(new TargetPlayer());
    }

    private FalkenrathNobleTriggeredAbility(final FalkenrathNobleTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public FalkenrathNobleTriggeredAbility copy() {
        return new FalkenrathNobleTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        if (!zEvent.isDiesEvent()) {
            return false;
        }
        Permanent permanent = (Permanent) game.getLastKnownInformation(event.getTargetId(), Zone.BATTLEFIELD);
        if (permanent == null) {
            return false;
        }
        if (permanent.getId().equals(this.getSourceId())) {
            return true;
        }
        return permanent.isCreature(game);
    }

    @Override
    public String getRule() {
        return "Whenever {this} or another creature dies, target player loses 1 life and you gain 1 life.";
    }
}
