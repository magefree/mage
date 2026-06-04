package mage.cards.c;

import java.util.Optional;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.DrawNthCardTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DoWhenCostPaid;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.turn.ControlTargetPlayerNextTurnEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.VillainToken;
import mage.target.common.TargetOpponent;

/**
 *
 * @author muz
 */
public final class ConstructACosmicCube extends CardImpl {

    public ConstructACosmicCube(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}");

        this.subtype.add(SubType.PLAN);

        // Whenever you draw your second card each turn, create a 2/1 black Villain creature token with menace and put a plan counter on this enchantment.
        Ability ability = new DrawNthCardTriggeredAbility(new CreateTokenEffect(new VillainToken()));
        ability.addEffect(new AddCountersSourceEffect(CounterType.PLAN.createInstance()).concatBy("and"));
        this.addAbility(ability);

        // When the seventh plan counter is put on this enchantment, sacrifice it. When you do, you control target opponent during their next turn.
        this.addAbility(new ConstructACosmicCubeCounterTriggeredAbility());
    }

    private ConstructACosmicCube(final ConstructACosmicCube card) {
        super(card);
    }

    @Override
    public ConstructACosmicCube copy() {
        return new ConstructACosmicCube(this);
    }
}

class ConstructACosmicCubeCounterTriggeredAbility extends TriggeredAbilityImpl {

    private static DoWhenCostPaid makeEffect() {
        ReflexiveTriggeredAbility reflexive = new ReflexiveTriggeredAbility(
            new ControlTargetPlayerNextTurnEffect(), false,
            "you control target opponent during their next turn"
        );
        reflexive.addTarget(new TargetOpponent());
        return new DoWhenCostPaid(reflexive, new SacrificeSourceCost().setText("sacrifice it"), "Sacrifice {this}?", false);
    }

    ConstructACosmicCubeCounterTriggeredAbility() {
        super(Zone.ALL, makeEffect(), false);
        setTriggerPhrase("When the seventh plan counter is put on {this}, ");
    }

    private ConstructACosmicCubeCounterTriggeredAbility(final ConstructACosmicCubeCounterTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public ConstructACosmicCubeCounterTriggeredAbility copy() {
        return new ConstructACosmicCubeCounterTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.COUNTER_ADDED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!event.getTargetId().equals(getSourceId()) || !event.getData().equals(CounterType.PLAN.getName())) {
            return false;
        }
        int amountAdded = event.getAmount();
        Permanent sourcePermanent = Optional
                .ofNullable(game.getPermanent(getSourceId()))
                .orElse(game.getPermanentEntering(getSourceId()));
        int planCounters;
        if (sourcePermanent != null) {
            planCounters = sourcePermanent.getCounters(game).getCount(CounterType.PLAN);
        } else {
            planCounters = amountAdded;
        }
        return planCounters - amountAdded < 7 && 7 <= planCounters;
    }
}
