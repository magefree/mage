package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesThisOrAnotherCreatureTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.SourceControllerCountersCount;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.counter.AddCountersPlayersEffect;
import mage.abilities.keyword.AnnihilatorAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorlessPredicate;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class AzlaskTheSwellingScourge extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("colorless creature you control");
    private static final FilterPermanent filter2 = new FilterPermanent("Scions and Spawns");

    static {
        filter.add(ColorlessPredicate.instance);
        filter2.add(Predicates.or(
                SubType.SCION.getPredicate(),
                SubType.SPAWN.getPredicate()
        ));
    }

    public AzlaskTheSwellingScourge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELDRAZI);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Azlask, the Swelling Scourge or another colorless creature you control dies, you get an experience counter.
        this.addAbility(new DiesThisOrAnotherCreatureTriggeredAbility(new AddCountersPlayersEffect(
                CounterType.EXPERIENCE.createInstance(), TargetController.YOU
        ), false, filter));

        // {W}{U}{B}{R}{G}: Creatures you control get +X/+X until end of turn, where X is the number of experience counters you have.
        // Scions and Spawns you control gain indestructible and annihilator 1 until end of turn.
        Ability ability = new SimpleActivatedAbility(new BoostControlledEffect(
                SourceControllerCountersCount.EXPERIENCE, SourceControllerCountersCount.EXPERIENCE, Duration.EndOfTurn
        ).setText("creatures you control get +X/+X until end of turn, where X is the number of the experience counters you have."), new ManaCostsImpl<>("{W}{U}{B}{R}{G}"));
        ability.addEffect(new GainAbilityControlledEffect(IndestructibleAbility.getInstance(), Duration.EndOfTurn, filter2)
                .setText("Scions and Spawns you control gain indestructible"));
        ability.addEffect(new GainAbilityControlledEffect(new AnnihilatorAbility(1), Duration.EndOfTurn, filter2)
                .concatBy("and").setText("annihilator 1 until end of turn"));
        this.addAbility(ability);
    }

    private AzlaskTheSwellingScourge(final AzlaskTheSwellingScourge card) {
        super(card);
    }

    @Override
    public AzlaskTheSwellingScourge copy() {
        return new AzlaskTheSwellingScourge(this);
    }
}
