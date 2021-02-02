
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterControlledEnchantmentPermanent;
import mage.filter.common.FilterControlledLandPermanent;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author LevelX2
 */
public final class Thaumatog extends CardImpl {

    public Thaumatog(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}{W}");
        this.subtype.add(SubType.ATOG);

        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Sacrifice a land: Thaumatog gets +1/+1 until end of turn.
        this.addAbility(new SimpleActivatedAbility(
                Zone.BATTLEFIELD,
                new BoostSourceEffect(1,1, Duration.EndOfTurn),
                new SacrificeTargetCost(new TargetControlledPermanent(new FilterControlledLandPermanent("land")))));
        // Sacrifice an enchantment: Thaumatog gets +1/+1 until end of turn.
        this.addAbility(new SimpleActivatedAbility(
                Zone.BATTLEFIELD,
                new BoostSourceEffect(1,1, Duration.EndOfTurn),
                new SacrificeTargetCost(new TargetControlledPermanent(new FilterControlledEnchantmentPermanent("enchantment")))));
    }

    private Thaumatog(final Thaumatog card) {
        super(card);
    }

    @Override
    public Thaumatog copy() {
        return new Thaumatog(this);
    }
}
