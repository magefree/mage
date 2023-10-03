
package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.CompositeCost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public final class VisceridDrone extends CardImpl {

    private static final FilterCreaturePermanent filter1 = new FilterCreaturePermanent("nonartifact creature");
    private static final FilterControlledPermanent filter2 = new FilterControlledPermanent("Swamp");
    private static final FilterControlledPermanent filter3 = new FilterControlledPermanent("snow Swamp");

    static {
        filter1.add(Predicates.not(CardType.ARTIFACT.getPredicate()));
        filter2.add(SubType.SWAMP.getPredicate());
        filter3.add(SubType.SWAMP.getPredicate());
        filter3.add(SuperType.SNOW.getPredicate());
    }

    public VisceridDrone(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.HOMARID);
        this.subtype.add(SubType.DRONE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // {tap}, Sacrifice a creature and a Swamp: Destroy target nonartifact creature. It can't be regenerated.
        Ability ability = new SimpleActivatedAbility(new DestroyTargetEffect(true), new TapSourceCost());
        ability.addCost(new CompositeCost(
                new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT),
                new SacrificeTargetCost(new TargetControlledPermanent(filter2)),
                "Sacrifice a creature and a Swamp"
        ));
        ability.addTarget(new TargetCreaturePermanent(filter1));
        this.addAbility(ability);

        // {tap}, Sacrifice a creature and a snow Swamp: Destroy target creature. It can't be regenerated.
        ability = new SimpleActivatedAbility(new DestroyTargetEffect(true), new TapSourceCost());
        ability.addCost(new CompositeCost(
                new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT),
                new SacrificeTargetCost(new TargetControlledPermanent(filter3)),
                "Sacrifice a creature and a snow Swamp"
        ));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private VisceridDrone(final VisceridDrone card) {
        super(card);
    }

    @Override
    public VisceridDrone copy() {
        return new VisceridDrone(this);
    }
}
