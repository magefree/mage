package mage.cards.o;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.BecomesMonarchSourceEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.hint.common.MonarchHint;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author muz
 */
public final class OkoyeMightyAndAdored extends CardImpl {

    public OkoyeMightyAndAdored(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Okoye enters, you become the monarch.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new BecomesMonarchSourceEffect()).addHint(MonarchHint.instance));

        // At the beginning of combat on your turn, put a +1/+1 counter on target creature. Whenever that creature attacks the monarch this turn, it gains double strike and trample until end of turn.
        Ability ability = new BeginningOfCombatTriggeredAbility(new AddCountersTargetEffect(CounterType.P1P1.createInstance()));
        ability.addEffect(new CreateDelayedTriggeredAbilityEffect(new OkoyeMightyAndAdoredDelayedTriggeredAbility()));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private OkoyeMightyAndAdored(final OkoyeMightyAndAdored card) {
        super(card);
    }

    @Override
    public OkoyeMightyAndAdored copy() {
        return new OkoyeMightyAndAdored(this);
    }
}

class OkoyeMightyAndAdoredDelayedTriggeredAbility extends DelayedTriggeredAbility {

    OkoyeMightyAndAdoredDelayedTriggeredAbility() {
        super(new GainAbilityTargetEffect(DoubleStrikeAbility.getInstance(), Duration.EndOfTurn)
            .setText("it gains double strike"), Duration.EndOfTurn, false, false);
        this.addEffect(new GainAbilityTargetEffect(TrampleAbility.getInstance(), Duration.EndOfTurn)
            .setText("and trample until end of turn"));
        this.setTriggerPhrase("Whenever that creature attacks the monarch this turn, ");
    }

    private OkoyeMightyAndAdoredDelayedTriggeredAbility(final OkoyeMightyAndAdoredDelayedTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public OkoyeMightyAndAdoredDelayedTriggeredAbility copy() {
        return new OkoyeMightyAndAdoredDelayedTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ATTACKER_DECLARED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getSourceId().equals(getEffects().get(0).getTargetPointer().getFirst(game, this))
            && event.getTargetId().equals(game.getMonarchId());
    }
}
