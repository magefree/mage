package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.PrototypeAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AutonomousAssembler extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.ASSEMBLY_WORKER);

    public AutonomousAssembler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{5}");

        this.subtype.add(SubType.ASSEMBLY_WORKER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Prototype {1}{W} -- 2/2
        this.addAbility(new PrototypeAbility(this, "{1}{W}", 2, 2));

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // {1}, {T}: Put a +1/+1 counter on target Assembly-Worker you control.
        Ability ability = new SimpleActivatedAbility(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance()), new GenericManaCost(1)
        );
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private AutonomousAssembler(final AutonomousAssembler card) {
        super(card);
    }

    @Override
    public AutonomousAssembler copy() {
        return new AutonomousAssembler(this);
    }
}
