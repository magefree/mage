package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanent;
import mage.abilities.keyword.MenaceAbility;
import mage.abilities.Ability;
import mage.abilities.common.DrawNthCardTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.BasicLandcyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class RoxxonBrutes extends CardImpl {

    public RoxxonBrutes(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.BERSERKER);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Menace
        this.addAbility(new MenaceAbility());

        // Whenever you draw your second card each turn, put a +1/+1 counter on target creature.
        Ability ability = new DrawNthCardTriggeredAbility(
            new AddCountersTargetEffect(CounterType.P1P1.createInstance()), false, 2
        );
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // Basic landcycling {2}
        this.addAbility(new BasicLandcyclingAbility(new ManaCostsImpl<>("{2}")));
    }

    private RoxxonBrutes(final RoxxonBrutes card) {
        super(card);
    }

    @Override
    public RoxxonBrutes copy() {
        return new RoxxonBrutes(this);
    }
}
