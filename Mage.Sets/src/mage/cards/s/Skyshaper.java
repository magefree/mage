
package mage.cards.s;

import java.util.UUID;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class Skyshaper extends CardImpl {

    public Skyshaper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{2}");

        // Sacrifice Skyshaper: Creatures you control gain flying until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, 
                new GainAbilityControlledEffect(
                        FlyingAbility.getInstance(), 
                        Duration.EndOfTurn, 
                        new FilterControlledCreaturePermanent("Creatures")), 
                new SacrificeSourceCost()));        
    }

    private Skyshaper(final Skyshaper card) {
        super(card);
    }

    @Override
    public Skyshaper copy() {
        return new Skyshaper(this);
    }
}
