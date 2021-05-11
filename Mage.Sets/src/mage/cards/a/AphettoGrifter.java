
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.common.TapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author LoneFox
 */
public final class AphettoGrifter extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("untapped Wizards you control");

    static {
        filter.add(TappedPredicate.UNTAPPED);
        filter.add(SubType.WIZARD.getPredicate());
    }

    public AphettoGrifter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Tap two untapped Wizards you control: Tap target permanent.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new TapTargetEffect(), new TapTargetCost(new TargetControlledPermanent(2, 2, filter, false)));
        ability.addTarget(new TargetPermanent());
        this.addAbility(ability);
    }

    private AphettoGrifter(final AphettoGrifter card) {
        super(card);
    }

    @Override
    public AphettoGrifter copy() {
        return new AphettoGrifter(this);
    }
}
