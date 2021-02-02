
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;

/**
 *
 * @author Wehk
 */
public final class SkullmeadCauldron extends CardImpl {

    public SkullmeadCauldron(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{4}");

        // {tap}: You gain 1 life.
        Ability ability1 = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainLifeEffect(1), new TapSourceCost());
        this.addAbility(ability1);
        
        // {tap}, Discard a card: You gain 3 life.
        Ability ability2 = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainLifeEffect(3), new TapSourceCost());
        ability2.addCost(new DiscardCardCost());
        this.addAbility(ability2);
    }

    private SkullmeadCauldron(final SkullmeadCauldron card) {
        super(card);
    }

    @Override
    public SkullmeadCauldron copy() {
        return new SkullmeadCauldron(this);
    }
}
