package mage.cards.h;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.NightboundAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.DamagedBatchEvent;
import mage.game.events.DamagedPermanentBatchEvent;
import mage.game.events.GameEvent;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HowlpackAvenger extends CardImpl {

    public HowlpackAvenger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");

        this.subtype.add(SubType.WEREWOLF);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);
        this.color.setRed(true);
        this.nightCard = true;

        // Whenever a permanent you control is dealt damage, Howlpack Avenger deals that much damage to any target.
        this.addAbility(new HowlpackAvengerTriggeredAbility());

        // {1}{R}: Howlpack Avenger gets +2/+0 until end of turn.
        this.addAbility(new SimpleActivatedAbility(new BoostSourceEffect(
                2, 0, Duration.EndOfTurn
        ), new ManaCostsImpl<>("{1}{R}")));

        // Nightbound
        this.addAbility(new NightboundAbility());
    }

    private HowlpackAvenger(final HowlpackAvenger card) {
        super(card);
    }

    @Override
    public HowlpackAvenger copy() {
        return new HowlpackAvenger(this);
    }
}

class HowlpackAvengerTriggeredAbility extends TriggeredAbilityImpl {

    HowlpackAvengerTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DamageTargetEffect(SavedDamageValue.MUCH));
        this.addTarget(new TargetAnyTarget());
        setTriggerPhrase("Whenever a permanent you control is dealt damage, ");
    }

    private HowlpackAvengerTriggeredAbility(final HowlpackAvengerTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public HowlpackAvengerTriggeredAbility copy() {
        return new HowlpackAvengerTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PERMANENT_BATCH;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        DamagedBatchEvent dEvent = (DamagedBatchEvent) event;
        int damage = dEvent
                .getEvents()
                .stream()
                .filter(damagedEvent -> isControlledBy(game.getControllerId(damagedEvent.getTargetId())))
                .mapToInt(GameEvent::getAmount)
                .sum();
        if (damage < 1) {
            return false;
        }
        this.getEffects().setValue("damage", damage);
        return true;
    }
}
