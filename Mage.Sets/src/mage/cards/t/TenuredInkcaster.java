package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksAllTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TenuredInkcaster extends CardImpl {

    private static final FilterCreaturePermanent filter
            = new FilterCreaturePermanent("creature you control with a +1/+1 counter on it");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
        filter.add(CounterType.P1P1.getPredicate());
    }

    public TenuredInkcaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}");

        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Tenured Inkcaster enters the battlefield, put a +1/+1 counter on target creature.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance())
        );
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // Whenever a creature you control with a +1/+1 counter on it attacks, each opponent loses 1 life and you gain 1 life.
        ability = new AttacksAllTriggeredAbility(
                new LoseLifeOpponentsEffect(1), false,
                filter, SetTargetPointer.NONE, false
        );
        ability.addEffect(new GainLifeEffect(1).concatBy("and"));
        this.addAbility(ability);
    }

    private TenuredInkcaster(final TenuredInkcaster card) {
        super(card);
    }

    @Override
    public TenuredInkcaster copy() {
        return new TenuredInkcaster(this);
    }
}
