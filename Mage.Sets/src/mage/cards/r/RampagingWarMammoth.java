package mage.cards.r;

import mage.MageInt;
import mage.abilities.common.ZoneChangeTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.hint.StaticHint;
import mage.abilities.keyword.CyclingAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.StackObject;
import mage.target.common.TargetArtifactPermanent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class RampagingWarMammoth extends CardImpl {

    public RampagingWarMammoth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{R}{R}");

        this.subtype.add(SubType.ELEPHANT);
        this.power = new MageInt(9);
        this.toughness = new MageInt(7);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Cycling {X}{2}{R}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{X}{2}{R}")));

        // When you cycle Rampaging War Mammoth, destroy up to X target artifacts.
        this.addAbility(new RampagingWarMammothTriggeredAbility());
    }

    private RampagingWarMammoth(final RampagingWarMammoth card) {
        super(card);
    }

    @Override
    public RampagingWarMammoth copy() {
        return new RampagingWarMammoth(this);
    }
}

class RampagingWarMammothTriggeredAbility extends ZoneChangeTriggeredAbility {

    RampagingWarMammothTriggeredAbility() {
        super(Zone.ALL, null, "", false);
    }

    private RampagingWarMammothTriggeredAbility(RampagingWarMammothTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ACTIVATED_ABILITY;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!event.getSourceId().equals(this.getSourceId())) {
            return false;
        }
        StackObject object = game.getStack().getStackObject(event.getSourceId());
        if (object == null || !(object.getStackAbility() instanceof CyclingAbility)) {
            return false;
        }

        CyclingAbility cyclingAbility = (CyclingAbility) object.getStackAbility();
        // If X is 0, or cycling from another ability that does not have {X} in cost,
        // this should trigger (but do nothing).
        int xValue = cyclingAbility.getManaCostsToPay().getX();

        this.getEffects().clear();
        this.getTargets().clear();

        this.addEffect(new DestroyTargetEffect());
        // Target up to X artifacts
        this.addTarget(new TargetArtifactPermanent(0, xValue));
        this.addHint(new StaticHint("X = " + xValue));

        return true;
    }

    @Override
    public RampagingWarMammothTriggeredAbility copy() {
        return new RampagingWarMammothTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "When you cycle {this}, destroy up to X target artifacts.";
    }
}
