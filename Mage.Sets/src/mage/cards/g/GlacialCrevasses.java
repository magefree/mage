
package mage.cards.g;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.PreventAllDamageByAllPermanentsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledLandPermanent;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author Quercitron
 */
public final class GlacialCrevasses extends CardImpl {

    private static final FilterControlledLandPermanent filter = new FilterControlledLandPermanent("a snow Mountain");

    static {
        filter.add(SubType.MOUNTAIN.getPredicate());
        filter.add(SuperType.SNOW.getPredicate());
    }

    public GlacialCrevasses(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{R}");

        // Sacrifice a snow Mountain: Prevent all combat damage that would be dealt this turn.
        Effect effect = new PreventAllDamageByAllPermanentsEffect(Duration.EndOfTurn, true);
        effect.setText("Prevent all combat damage that would be dealt this turn");
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new SacrificeTargetCost(new TargetControlledPermanent(filter)));
        this.addAbility(ability);
    }

    private GlacialCrevasses(final GlacialCrevasses card) {
        super(card);
    }

    @Override
    public GlacialCrevasses copy() {
        return new GlacialCrevasses(this);
    }
}
