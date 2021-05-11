
package mage.cards.n;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author Loki
 */
public final class NullmageShepherd extends CardImpl {

    private static final FilterControlledCreaturePermanent filterCost = new FilterControlledCreaturePermanent("untapped creatures you control");
    static {
        filterCost.add(TappedPredicate.UNTAPPED);
    }

    public NullmageShepherd(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Tap four untapped creatures you control: Destroy target artifact or enchantment.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DestroyTargetEffect(), new TapTargetCost(new TargetControlledCreaturePermanent(4, 4, filterCost, true)));
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_ENCHANTMENT));
        this.addAbility(ability);
    }

    private NullmageShepherd(final NullmageShepherd card) {
        super(card);
    }

    @Override
    public NullmageShepherd copy() {
        return new NullmageShepherd(this);
    }
}
