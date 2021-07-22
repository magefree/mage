
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FlyingAbility;
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
 * @author Loki
 */
public final class Telethopter extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("untapped creature you control");

    static {
        filter.add(TappedPredicate.UNTAPPED);
    }

    public Telethopter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{4}");
        this.subtype.add(SubType.THOPTER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainAbilitySourceEffect(FlyingAbility.getInstance(), Duration.EndOfTurn), new TapTargetCost(new TargetControlledCreaturePermanent(1, 1, filter, false))));
    }

    private Telethopter(final Telethopter card) {
        super(card);
    }

    @Override
    public Telethopter copy() {
        return new Telethopter(this);
    }
}
