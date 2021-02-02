
package mage.cards.s;

import java.util.UUID;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.CascadeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;

/**
 *
 * @author Loki
 */
public final class StormcallersBoon extends CardImpl {

    public StormcallersBoon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{W}{U}");




        // Sacrifice Stormcaller's Boon: Creatures you control gain flying until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainAbilityControlledEffect(FlyingAbility.getInstance(), Duration.EndOfTurn, new FilterControlledCreaturePermanent("Creatures")), new SacrificeSourceCost()));
        this.addAbility(new CascadeAbility());
    }

    private StormcallersBoon(final StormcallersBoon card) {
        super(card);
    }

    @Override
    public StormcallersBoon copy() {
        return new StormcallersBoon(this);
    }
}
