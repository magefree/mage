package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealtDamageToSourceTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.common.counter.AddCountersPlayersEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;

import java.util.UUID;

public class StrongTheBrutishThespian extends CardImpl {
    public StrongTheBrutishThespian(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}{G}");
        this.addSuperType(SuperType.LEGENDARY);
        this.addSubType(SubType.MUTANT);
        this.addSubType(SubType.BERSERKER);

        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        // Ward {2}
        this.addAbility(new WardAbility(new ManaCostsImpl<>("{2}")));

        // Enrage - Whenever strong is dealt damage, you get three rad counters and put three +1/+1 counters on Strong.
        Ability enrageAbility = new DealtDamageToSourceTriggeredAbility(new AddCountersPlayersEffect(CounterType.RAD.createInstance(3), TargetController.YOU), false, true);
        enrageAbility.addEffect(new AddCountersSourceEffect(CounterType.P1P1.createInstance(3)).setText("put three +1/+1 counters on Strong"));
        this.addAbility(enrageAbility);

        // You gain life rather than lose life from radiation.
        Ability healAbility = new SimpleStaticAbility(new StrongTheBrutishThespianHealEffect().setText("You gain life rather than lose life from radiation."));
        this.addAbility(healAbility);
    }

    public StrongTheBrutishThespian(StrongTheBrutishThespian card) {
        super(card);
    }

    @Override
    public StrongTheBrutishThespian copy() {
        return new StrongTheBrutishThespian(this);
    }

    class StrongTheBrutishThespianHealEffect extends ContinuousRuleModifyingEffectImpl {

        protected StrongTheBrutishThespianHealEffect() {
            super(Duration.Custom, Outcome.Benefit);
        }

        public StrongTheBrutishThespianHealEffect(StrongTheBrutishThespianHealEffect effect) {
            super(effect);
        }

        @Override
        public StrongTheBrutishThespianHealEffect copy() {
            return new StrongTheBrutishThespianHealEffect(this);
        }

        @Override
        public boolean checksEventType(GameEvent event, Game game) {
            return event.getType() == GameEvent.EventType.RADIATION_GAIN_LIFE;
        }

        @Override
        public boolean applies(GameEvent event, Ability source, Game game) {
            return event.getPlayerId().equals(source.getControllerId());
        }
    }
}
