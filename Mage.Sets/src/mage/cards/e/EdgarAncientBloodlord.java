package mage.cards.e;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreatureOrPlaneswalkerPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;

/**
 *
 * @author muz
 */
public final class EdgarAncientBloodlord extends CardImpl {

    private static final FilterPermanent filter
        = new FilterCreatureOrPlaneswalkerPermanent("another creature or planeswalker you control");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public EdgarAncientBloodlord(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}{B}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Whenever another creature or planeswalker you control dies, you gain 1 life.
        this.addAbility(new DiesCreatureTriggeredAbility(
            new GainLifeEffect(1), false, filter
        ));

        // {2}, Sacrifice another creature or planeswalker: Put a +1/+1 counter on Edgar. He gains menace until end of turn.
        Ability ability = new SimpleActivatedAbility(
            new AddCountersSourceEffect(CounterType.P1P1.createInstance())
                .setText("Put a +1/+1 counter on {this}."),
            new ManaCostsImpl<>("{2}")
        );
        ability.addCost(new SacrificeTargetCost(filter));
        ability.addEffect(new GainAbilitySourceEffect(new MenaceAbility(), Duration.EndOfTurn)
            .setText("He gains menace until end of turn"));
        this.addAbility(ability);
    }

    private EdgarAncientBloodlord(final EdgarAncientBloodlord card) {
        super(card);
    }

    @Override
    public EdgarAncientBloodlord copy() {
        return new EdgarAncientBloodlord(this);
    }
}
