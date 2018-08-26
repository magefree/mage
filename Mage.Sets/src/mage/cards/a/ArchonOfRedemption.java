
package mage.cards.a;

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
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;

/**
 * @author Loki
 */
public final class ArchonOfRedemption extends CardImpl {

    public ArchonOfRedemption(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}{W}");
        this.subtype.add(SubType.ARCHON);

        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        this.addAbility(FlyingAbility.getInstance());
        // Whenever Archon of Redemption or another creature with flying enters the battlefield under your control, you may gain life equal to that creature's power.
        this.addAbility(new ArchonOfRedemptionTriggeredAbility());
    }

    public ArchonOfRedemption(final ArchonOfRedemption card) {
        super(card);
    }

    @Override
    public ArchonOfRedemption copy() {
        return new ArchonOfRedemption(this);
    }
}

class ArchonOfRedemptionTriggeredAbility extends TriggeredAbilityImpl {

    ArchonOfRedemptionTriggeredAbility() {
        super(Zone.BATTLEFIELD, null, true);
    }

    ArchonOfRedemptionTriggeredAbility(final ArchonOfRedemptionTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public ArchonOfRedemptionTriggeredAbility copy() {
        return new ArchonOfRedemptionTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanent(event.getTargetId());
        if (permanent.isControlledBy(getControllerId())
                && permanent.isCreature()
                && (permanent.getId().equals(getSourceId())
                || (permanent.getAbilities().contains(FlyingAbility.getInstance())))) {
            this.getEffects().clear();
            this.addEffect(new GainLifeEffect(permanent.getPower().getValue()));
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever {this} or another creature with flying enters the battlefield under your control, you may gain life equal to that creature's power.";
    }
}
