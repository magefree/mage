
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SuperType;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author TheElk801
 */
public final class KarplusanGiant extends CardImpl {

    private static final FilterControlledLandPermanent filter = new FilterControlledLandPermanent("untapped snow land you control");

    static {
        filter.add(TappedPredicate.UNTAPPED);
        filter.add(SuperType.SNOW.getPredicate());
    }

    public KarplusanGiant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{6}{R}");

        this.subtype.add(SubType.GIANT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Tap an untapped snow land you control: Karplusan Giant gets +1/+1 until end of turn.
        this.addAbility(new SimpleActivatedAbility(
                new BoostSourceEffect(1, 1, Duration.EndOfTurn),
                new TapTargetCost(new TargetControlledPermanent(filter))
        ));
    }

    private KarplusanGiant(final KarplusanGiant card) {
        super(card);
    }

    @Override
    public KarplusanGiant copy() {
        return new KarplusanGiant(this);
    }
}
