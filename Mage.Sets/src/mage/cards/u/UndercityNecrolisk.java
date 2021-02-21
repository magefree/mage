package mage.cards.u;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author TheElk801
 */
public final class UndercityNecrolisk extends CardImpl {

    private static final FilterControlledPermanent filter
            = new FilterControlledCreaturePermanent("another creature");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public UndercityNecrolisk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.LIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // {1}, Sacrifice another creature: Put a +1/+1 counter on Undercity Necrolisk. It gains menace until end of turn. Activate this ability only any time you could cast a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(
                Zone.BATTLEFIELD,
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()),
                new GenericManaCost(1)
        );
        ability.addEffect(new GainAbilitySourceEffect(
                new MenaceAbility(),
                Duration.EndOfTurn
        ).setText("It gains menace until end of turn."));
        ability.addCost(new SacrificeTargetCost(
                new TargetControlledPermanent(filter)
        ));
        this.addAbility(ability);
    }

    private UndercityNecrolisk(final UndercityNecrolisk card) {
        super(card);
    }

    @Override
    public UndercityNecrolisk copy() {
        return new UndercityNecrolisk(this);
    }
}
