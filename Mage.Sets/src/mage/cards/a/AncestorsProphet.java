
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author Wehk
 */
public final class AncestorsProphet extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("untapped Clerics you control");
    
    static {
        filter.add(SubType.CLERIC.getPredicate());
        filter.add(TappedPredicate.UNTAPPED);
    }    
    
    public AncestorsProphet(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(1);
        this.toughness = new MageInt(5);

        // Tap five untapped Clerics you control: You gain 10 life.
        SimpleActivatedAbility ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new GainLifeEffect(10),
                new TapTargetCost(new TargetControlledPermanent(5, 5, filter, true)));
        this.addAbility(ability);
    }

    private AncestorsProphet(final AncestorsProphet card) {
        super(card);
    }

    @Override
    public AncestorsProphet copy() {
        return new AncestorsProphet(this);
    }
}
