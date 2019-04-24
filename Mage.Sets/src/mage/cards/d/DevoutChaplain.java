
package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;


/**
 * @author noxx
 */
public final class DevoutChaplain extends CardImpl {
    private static final FilterControlledPermanent humanFilter = new FilterControlledPermanent("untapped Human you control");

    static {
        humanFilter.add(Predicates.not(new TappedPredicate()));
        humanFilter.add(new SubtypePredicate(SubType.HUMAN));
    }

    public DevoutChaplain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);

        this.color.setWhite(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {T}, Tap two untapped Humans you control: Exile target artifact or enchantment.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ExileTargetEffect(), new TapSourceCost());
        ability.addCost(new TapTargetCost(new TargetControlledPermanent(2, 2, humanFilter, false)));
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_ENCHANTMENT));
        this.addAbility(ability);
    }

    public DevoutChaplain(final DevoutChaplain card) {
        super(card);
    }

    @Override
    public DevoutChaplain copy() {
        return new DevoutChaplain(this);
    }
}
