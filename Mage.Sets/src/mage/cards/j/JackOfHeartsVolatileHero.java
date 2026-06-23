package mage.cards.j;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerValue;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.discard.DiscardHandControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.PowerUpAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class JackOfHeartsVolatileHero extends CardImpl {

    public JackOfHeartsVolatileHero(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ALIEN);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // When Jack of Hearts dies, he deals damage equal to his power to each creature.
        this.addAbility(new DiesSourceTriggeredAbility(
            new DamageAllEffect(SourcePermanentPowerValue.NOT_NEGATIVE, new FilterCreaturePermanent())
                .setText("he deals damage equal to his power to each creature")
        ));

        // Power-up -- {4}{R}{R}: Discard your hand, then draw three cards. Put two +1/+1 counters on Jack of Hearts.
        Ability ability = new PowerUpAbility(
            new DiscardHandControllerEffect(),
            new ManaCostsImpl<>("{4}{R}{R}")
        );
        ability.addEffect(new DrawCardSourceControllerEffect(3).concatBy(", then"));
        ability.addEffect(new AddCountersSourceEffect(CounterType.P1P1.createInstance(2)));
        this.addAbility(ability);
    }

    private JackOfHeartsVolatileHero(final JackOfHeartsVolatileHero card) {
        super(card);
    }

    @Override
    public JackOfHeartsVolatileHero copy() {
        return new JackOfHeartsVolatileHero(this);
    }
}
