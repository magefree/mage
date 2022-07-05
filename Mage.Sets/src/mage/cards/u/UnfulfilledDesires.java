
package mage.cards.u;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;

/**
 *
 * @author LoneFox
 */
public final class UnfulfilledDesires extends CardImpl {

    public UnfulfilledDesires(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{U}{B}");

        // {1}, Pay 1 life: Draw a card, then discard a card.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DrawDiscardControllerEffect(), new ManaCostsImpl<>("{1}"));
        ability.addCost(new PayLifeCost(1));
        this.addAbility(ability);
    }

    private UnfulfilledDesires(final UnfulfilledDesires card) {
        super(card);
    }

    @Override
    public UnfulfilledDesires copy() {
        return new UnfulfilledDesires(this);
    }
}
