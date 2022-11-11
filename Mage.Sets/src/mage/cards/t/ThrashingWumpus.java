
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageEverythingEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 *
 * @author LoneFox

 */
public final class ThrashingWumpus extends CardImpl {

    public ThrashingWumpus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}{B}");
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // {B}: Thrashing Wumpus deals 1 damage to each creature and each player.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageEverythingEffect(1), new ManaCostsImpl<>("{B}")));
    }

    private ThrashingWumpus(final ThrashingWumpus card) {
        super(card);
    }

    @Override
    public ThrashingWumpus copy() {
        return new ThrashingWumpus(this);
    }
}
