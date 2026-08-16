package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanent;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.BasicLandcyclingAbility;
import mage.abilities.keyword.DauntAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class ColdBloodedCrew extends CardImpl {

    public ColdBloodedCrew(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}{G}");

        this.subtype.add(SubType.GORN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // When this creature enters, put two +1/+1 counters on target creature.
        Ability ability = new EntersBattlefieldTriggeredAbility(new AddCountersTargetEffect(CounterType.P1P1.createInstance(2)));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // This creature can't be blocked by creatures with power 2 or less.
        this.addAbility(new DauntAbility());

        // Basic landcycling {2}
        this.addAbility(new BasicLandcyclingAbility(new ManaCostsImpl<>("{2}")));
    }

    private ColdBloodedCrew(final ColdBloodedCrew card) {
        super(card);
    }

    @Override
    public ColdBloodedCrew copy() {
        return new ColdBloodedCrew(this);
    }
}
