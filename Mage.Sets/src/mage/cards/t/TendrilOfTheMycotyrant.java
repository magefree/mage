package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BecomesCreatureTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.game.permanent.token.custom.CreatureToken;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class TendrilOfTheMycotyrant extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("noncreature land you control");

    static {
        filter.add(Predicates.not(CardType.CREATURE.getPredicate()));
        filter.add(CardType.LAND.getPredicate());
    }

    public TendrilOfTheMycotyrant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.FUNGUS);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {5}{G}{G}: Put seven +1/+1 counters on target noncreature land you control. It becomes a 0/0 Fungus creature with haste. It's still a land.
        Ability ability = new SimpleActivatedAbility(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance(7)),
                new ManaCostsImpl<>("{5}{G}{G}")
        );
        ability.addEffect(new BecomesCreatureTargetEffect(
                new CreatureToken(0, 0, "", SubType.FUNGUS)
                        .withAbility(HasteAbility.getInstance()),
                false, true, Duration.Custom
        ).setText("It becomes a 0/0 Fungus creature with haste. It's still a land."));
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private TendrilOfTheMycotyrant(final TendrilOfTheMycotyrant card) {
        super(card);
    }

    @Override
    public TendrilOfTheMycotyrant copy() {
        return new TendrilOfTheMycotyrant(this);
    }
}
