
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.ConvokeAbility;
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
 * @author emerald000
 */
public final class RootKinAlly extends CardImpl {
    
    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("untapped creatures you control");
    static {
        filter.add(TappedPredicate.UNTAPPED);
    }

    public RootKinAlly(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{G}{G}");
        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Convoke
        this.addAbility(new ConvokeAbility());
        
        // Tap two untapped creatures you control: Root-Kin Ally gets +2/+2 until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostSourceEffect(2, 2, Duration.EndOfTurn), new TapTargetCost(new TargetControlledCreaturePermanent(2, 2, filter, true))));
    }

    private RootKinAlly(final RootKinAlly card) {
        super(card);
    }

    @Override
    public RootKinAlly copy() {
        return new RootKinAlly(this);
    }
}
