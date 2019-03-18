
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterNonlandPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetNonlandPermanent;

/**
 *
 * @author TheElk801
 */
public final class GalecasterColossus extends CardImpl {

    private static final FilterNonlandPermanent filter = new FilterNonlandPermanent("nonland permanent you don't control");
    private static final FilterControlledPermanent filter2 = new FilterControlledPermanent("untapped Wizard you control");

    static {
        filter.add(new ControllerPredicate(TargetController.NOT_YOU));
        filter2.add(new SubtypePredicate(SubType.WIZARD));
        filter2.add(Predicates.not(TappedPredicate.instance));
    }

    public GalecasterColossus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{U}{U}");

        this.subtype.add(SubType.GIANT);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(5);
        this.toughness = new MageInt(6);

        // Tap an untapped Wizard you control: Return target nonland permanent you don't control to its owner's hand.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ReturnToHandTargetEffect(), new TapTargetCost(new TargetControlledPermanent(1, 1, filter2, true)));
        ability.addTarget(new TargetNonlandPermanent(filter));
        this.addAbility(ability);
    }

    public GalecasterColossus(final GalecasterColossus card) {
        super(card);
    }

    @Override
    public GalecasterColossus copy() {
        return new GalecasterColossus(this);
    }
}
