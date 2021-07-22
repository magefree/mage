
package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author Plopman
 */
public final class LlanowarBehemoth extends CardImpl {

    private static final FilterControlledCreaturePermanent filter =  new FilterControlledCreaturePermanent("untapped creature you control");
    static {
        filter.add(TappedPredicate.UNTAPPED);
    }
    
    public LlanowarBehemoth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}{G}");
        this.subtype.add(SubType.ELEMENTAL);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Tap an untapped creature you control: Llanowar Behemoth gets +1/+1 until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostSourceEffect(1, 1, Duration.EndOfTurn), new TapTargetCost(new TargetControlledCreaturePermanent(1, 1, filter, false))));
    }

    private LlanowarBehemoth(final LlanowarBehemoth card) {
        super(card);
    }

    @Override
    public LlanowarBehemoth copy() {
        return new LlanowarBehemoth(this);
    }
}
