package mage.cards.t;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DoWhenCostPaid;
import mage.abilities.effects.common.combat.CantBeBlockedTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetAttackingCreature;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TaekoThePatientAvalanche extends CardImpl {

    public TaekoThePatientAvalanche(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.TURTLE);
        this.subtype.add(SubType.NINJA);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Taeko, the Patient Avalanche enters tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // Whenever another creature you control leaves the battlefield, if it didn't die, scry 1 and put a +1/+1 counter on Taeko.
        this.addAbility(new TaekoThePatientAvalancheTriggeredAbility());

        // Whenever Taeko attacks, you may pay {U/B}. When you do, target attacking creature can't be blocked this turn.
        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(new CantBeBlockedTargetEffect(), false);
        ability.addTarget(new TargetAttackingCreature());
        this.addAbility(new AttacksTriggeredAbility(new DoWhenCostPaid(ability, new ManaCostsImpl<>("{U/B}"), "Pay {U/B}?")));
    }

    private TaekoThePatientAvalanche(final TaekoThePatientAvalanche card) {
        super(card);
    }

    @Override
    public TaekoThePatientAvalanche copy() {
        return new TaekoThePatientAvalanche(this);
    }
}

class TaekoThePatientAvalancheTriggeredAbility extends TriggeredAbilityImpl {

    TaekoThePatientAvalancheTriggeredAbility() {
        super(Zone.BATTLEFIELD, new ScryEffect(1, false));
        this.addEffect(new AddCountersSourceEffect(CounterType.P1P1.createInstance()).concatBy("and"));
        this.setTriggerPhrase("Whenever another creature you control leaves the battlefield, if it didn't die, ");
        setLeavesTheBattlefieldTrigger(true);
    }

    private TaekoThePatientAvalancheTriggeredAbility(final TaekoThePatientAvalancheTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public TaekoThePatientAvalancheTriggeredAbility copy() {
        return new TaekoThePatientAvalancheTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        if (!Zone.BATTLEFIELD.match(zEvent.getFromZone())
                || Zone.GRAVEYARD.match(zEvent.getToZone())) {
            return false;
        }
        Permanent permanent = zEvent.getTarget();
        return permanent != null
                && !permanent.getId().equals(getSourceId())
                && permanent.isControlledBy(getControllerId())
                && permanent.isCreature(game);
    }
}
