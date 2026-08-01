package mage.cards.g;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.mana.BlackManaAbility;
import mage.abilities.mana.RedManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.target.TargetPermanent;

/**
 *
 * @author muz
 */
public final class GoblinTown extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent("Goblin or Orc you control");

    static {
        filter.add(Predicates.or(
            SubType.GOBLIN.getPredicate(),
            SubType.ORC.getPredicate()
        ));
    }

    public GoblinTown(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // This land enters tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // {T}: Add {B} or {R}.
        this.addAbility(new BlackManaAbility());
        this.addAbility(new RedManaAbility());

        // {2}{B}{R}, {T}, Sacrifice this land: Put two +1/+1 counters on target Goblin or Orc you control. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(
            new AddCountersTargetEffect(CounterType.P1P1.createInstance(2)),
            new ManaCostsImpl<>("{2}{B}{R}")
        );
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private GoblinTown(final GoblinTown card) {
        super(card);
    }

    @Override
    public GoblinTown copy() {
        return new GoblinTown(this);
    }
}
