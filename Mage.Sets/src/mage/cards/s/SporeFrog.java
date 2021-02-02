
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.common.PreventAllDamageByAllPermanentsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author Plopman
 */
public final class SporeFrog extends CardImpl {

    public SporeFrog(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{G}");
        this.subtype.add(SubType.FROG);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Sacrifice Spore Frog: Prevent all combat damage that would be dealt this turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new PreventAllDamageByAllPermanentsEffect(Duration.EndOfTurn, true), new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private SporeFrog(final SporeFrog card) {
        super(card);
    }

    @Override
    public SporeFrog copy() {
        return new SporeFrog(this);
    }
}
