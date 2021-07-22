
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.keyword.FlyingAbility;
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
public final class KeeperOfTheNineGales extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("untapped Birds you control");

    static {
        filter.add(TappedPredicate.UNTAPPED);
        filter.add(SubType.BIRD.getPredicate());
    }

    public KeeperOfTheNineGales(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}");
        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // {tap}, Tap two untapped Birds you control: Return target permanent to its owner's hand.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ReturnToHandTargetEffect(), new TapSourceCost());
        ability.addCost(new TapTargetCost(new TargetControlledPermanent(2, 2, filter, false)));
        ability.addTarget(new TargetPermanent());
        this.addAbility(ability);
    }

    private KeeperOfTheNineGales(final KeeperOfTheNineGales card) {
        super(card);
    }

    @Override
    public KeeperOfTheNineGales copy() {
        return new KeeperOfTheNineGales(this);
    }
}
