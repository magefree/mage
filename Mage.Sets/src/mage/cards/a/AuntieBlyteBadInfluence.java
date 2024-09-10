package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveVariableCountersSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AuntieBlyteBadInfluence extends CardImpl {

    public AuntieBlyteBadInfluence(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DEVIL);
        this.subtype.add(SubType.ADVISOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever a source you control deals damage to you, put that many +1/+1 counters on Auntie Blyte, Bad Influence.
        this.addAbility(new AuntieBlyteBadInfluenceTriggeredAbility());

        // {1}{R}, {T}, Remove X +1/+1 counters from Auntie Blyte: It deals X damage to any target.
        Ability ability = new SimpleActivatedAbility(
                new DamageTargetEffect(GetXValue.instance, "it"), new ManaCostsImpl<>("{1}{R}")
        );
        ability.addCost(new TapSourceCost());
        ability.addCost(new RemoveVariableCountersSourceCost(CounterType.P1P1));
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private AuntieBlyteBadInfluence(final AuntieBlyteBadInfluence card) {
        super(card);
    }

    @Override
    public AuntieBlyteBadInfluence copy() {
        return new AuntieBlyteBadInfluence(this);
    }
}

class AuntieBlyteBadInfluenceTriggeredAbility extends TriggeredAbilityImpl {

    AuntieBlyteBadInfluenceTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AddCountersSourceEffect(
                CounterType.P1P1.createInstance(0), SavedDamageValue.MANY, false
        ).setText("put that many +1/+1 counters on {this}"));
        setTriggerPhrase("Whenever a source you control deals damage to you, ");
    }

    private AuntieBlyteBadInfluenceTriggeredAbility(final AuntieBlyteBadInfluenceTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public AuntieBlyteBadInfluenceTriggeredAbility copy() {
        return new AuntieBlyteBadInfluenceTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (isControlledBy(event.getTargetId()) && isControlledBy(game.getControllerId(event.getSourceId()))) {
            this.getEffects().setValue("damage", event.getAmount());
            return true;
        }
        return false;
    }
}
