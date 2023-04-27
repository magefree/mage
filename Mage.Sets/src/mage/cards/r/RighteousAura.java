
package mage.cards.r;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.PreventNextDamageFromChosenSourceToYouEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author anonymous
 */
public final class RighteousAura extends CardImpl {

    public RighteousAura(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{W}");

        // {W}, Pay 2 life: The next time a source of your choice would deal damage to you this turn, prevent that damage.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new PreventNextDamageFromChosenSourceToYouEffect(Duration.EndOfTurn), new ManaCostsImpl<>("{W}"));
        ability.addCost(new PayLifeCost(2));
        this.addAbility(ability);
    }

    private RighteousAura(final RighteousAura card) {
        super(card);
    }

    @Override
    public RighteousAura copy() {
        return new RighteousAura(this);
    }
}
