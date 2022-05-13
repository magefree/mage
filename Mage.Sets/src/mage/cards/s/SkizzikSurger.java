
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.keyword.EchoAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledLandPermanent;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author LoneFox
 */
public final class SkizzikSurger extends CardImpl {

    public SkizzikSurger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{R}{R}");
        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(6);
        this.toughness = new MageInt(4);

        // Haste
        this.addAbility(HasteAbility.getInstance());
        // Echo-Sacrifice two lands.
        this.addAbility(new EchoAbility(new SacrificeTargetCost(new TargetControlledPermanent(2, 2, new FilterControlledLandPermanent("lands"), true))));
    }

    private SkizzikSurger(final SkizzikSurger card) {
        super(card);
    }

    @Override
    public SkizzikSurger copy() {
        return new SkizzikSurger(this);
    }
}
