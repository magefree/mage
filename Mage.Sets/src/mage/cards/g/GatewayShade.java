
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author LevelX2
 */
public final class GatewayShade extends CardImpl {

    private static final FilterControlledLandPermanent filter = new FilterControlledLandPermanent("untapped Gate you control");
    static {
        filter.add(SubType.GATE.getPredicate());
        filter.add(TappedPredicate.UNTAPPED);
    }

    public GatewayShade(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}");
        this.subtype.add(SubType.SHADE);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {B}: Gateway Shade gets +1/+1 until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostSourceEffect(1,1, Duration.EndOfTurn),new ManaCostsImpl("{B}")));

        // Tap an untapped Gate you control: Gateway Shade gets +2/+2 until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostSourceEffect(2,2, Duration.EndOfTurn),new TapTargetCost(new TargetControlledPermanent(1,1,filter, true))));

    }

    private GatewayShade(final GatewayShade card) {
        super(card);
    }

    @Override
    public GatewayShade copy() {
        return new GatewayShade(this);
    }
}
