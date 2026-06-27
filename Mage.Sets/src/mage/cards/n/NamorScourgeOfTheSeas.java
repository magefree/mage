package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.keyword.ConniveTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author muz
 */
public final class NamorScourgeOfTheSeas extends CardImpl {

private static final FilterControlledCreaturePermanent filter =
    new FilterControlledCreaturePermanent(SubType.MERFOLK, "Merfolk you control with a +1/+1 counter on it");

  static {
      filter.add(CounterType.P1P1.getPredicate());
  }

    public NamorScourgeOfTheSeas(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.MUTANT);
        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Each other Merfolk you control with a +1/+1 counter on it has flying.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
            FlyingAbility.getInstance(),
            Duration.WhileOnBattlefield,
            filter,
            true
        )));

        // At the beginning of combat on your turn, target creature you control connives.
        Ability ability = new BeginningOfCombatTriggeredAbility(new ConniveTargetEffect());
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);
    }

    private NamorScourgeOfTheSeas(final NamorScourgeOfTheSeas card) {
        super(card);
    }

    @Override
    public NamorScourgeOfTheSeas copy() {
        return new NamorScourgeOfTheSeas(this);
    }
}
