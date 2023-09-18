package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.ZoneChangeTriggeredAbility;
import mage.abilities.effects.common.ExileThenReturnTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Soulherder extends CardImpl {

    public Soulherder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{U}");

        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever a creature is exiled from the battlefield, put a +1/+1 counter on Soulherder.
        this.addAbility(new SoulherderTriggeredAbility());

        // At the beginning of your end step, you may exile another target creature you control, then return that card to the battlefield under its owner's control.
        Ability ability = new BeginningOfEndStepTriggeredAbility(
                new ExileThenReturnTargetEffect(false, true), TargetController.YOU, true
        );
        ability.addTarget(new TargetControlledCreaturePermanent(StaticFilters.FILTER_ANOTHER_TARGET_CREATURE_YOU_CONTROL));
        this.addAbility(ability);
    }

    private Soulherder(final Soulherder card) {
        super(card);
    }

    @Override
    public Soulherder copy() {
        return new Soulherder(this);
    }
}

class SoulherderTriggeredAbility extends ZoneChangeTriggeredAbility {

    SoulherderTriggeredAbility() {
        super(Zone.BATTLEFIELD,
                Zone.BATTLEFIELD, Zone.EXILED,
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()),
                "Whenever a creature is exiled from the battlefield, ", false
        );
    }

    private SoulherderTriggeredAbility(final SoulherderTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public SoulherderTriggeredAbility copy() {
        return new SoulherderTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanentOrLKIBattlefield(event.getTargetId());
        if (permanent != null && permanent.isCreature(game)) {
            // custom check cause ZoneChangeTriggeredAbility for source object only
            ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
            return (fromZone == null || zEvent.getFromZone() == fromZone)
                    && (zEvent.getToZone() == toZone || zEvent.getOriginalToZone() == toZone);
        }
        return false;
    }
}
