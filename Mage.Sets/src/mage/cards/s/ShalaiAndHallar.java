package mage.cards.s;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ShalaiAndHallar extends CardImpl {

    public ShalaiAndHallar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{G}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ANGEL);
        this.subtype.add(SubType.ELF);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Whenever one or more +1/+1 counters are put on a creature you control, Shalai and Hallar deals that much damage to target opponent.
        this.addAbility(new ShalaiAndHallarTriggeredAbility());
    }

    private ShalaiAndHallar(final ShalaiAndHallar card) {
        super(card);
    }

    @Override
    public ShalaiAndHallar copy() {
        return new ShalaiAndHallar(this);
    }
}

class ShalaiAndHallarTriggeredAbility extends TriggeredAbilityImpl {

    ShalaiAndHallarTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DamageTargetEffect(SavedDamageValue.MUCH));
        this.addTarget(new TargetOpponent());
    }

    private ShalaiAndHallarTriggeredAbility(final ShalaiAndHallarTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public ShalaiAndHallarTriggeredAbility copy() {
        return new ShalaiAndHallarTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.COUNTERS_ADDED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!event.getData().equals(CounterType.P1P1.getName())
                || event.getAmount() < 1) {
            return false;
        }
        Permanent permanent = game.getPermanentOrLKIBattlefield(event.getTargetId());
        if (permanent == null) {
            permanent = game.getPermanentEntering(event.getTargetId());
        }
        if (permanent == null
                || !permanent.isControlledBy(this.getControllerId())
                || !permanent.isCreature(game)) {
            return false;
        }
        this.getEffects().setValue("damage", event.getAmount());
        return true;
    }

    @Override
    public String getRule() {
        return "Whenever one or more +1/+1 counters are put on a creature you control, " +
                "{this} deals that much damage to target opponent.";
    }
}
