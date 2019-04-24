
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LoneFox
 */
public final class DwarvenBloodboiler extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("an untapped Dwarf you control");

    static {
        filter.add(Predicates.not(new TappedPredicate()));
        filter.add(new SubtypePredicate(SubType.DWARF));
    }

    public DwarvenBloodboiler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{R}{R}{R}");
        this.subtype.add(SubType.DWARF);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Tap an untapped Dwarf you control: Target creature gets +2/+0 until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostTargetEffect(2, 0, Duration.EndOfTurn),
            new TapTargetCost(new TargetControlledPermanent(1, 1, filter, false)));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    public DwarvenBloodboiler(final DwarvenBloodboiler card) {
        super(card);
    }

    @Override
    public DwarvenBloodboiler copy() {
        return new DwarvenBloodboiler(this);
    }
}
