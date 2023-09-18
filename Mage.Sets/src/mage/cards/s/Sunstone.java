
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.PreventAllDamageByAllPermanentsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledLandPermanent;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author Quercitron
 */
public final class Sunstone extends CardImpl {

    private static final FilterControlledLandPermanent filter = new FilterControlledLandPermanent("a snow land");

    static {
        filter.add(SuperType.SNOW.getPredicate());
    }

    public Sunstone(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{3}");

        // {2}, Sacrifice a snow land: Prevent all combat damage that would be dealt this turn.
        Effect effect = new PreventAllDamageByAllPermanentsEffect(Duration.EndOfTurn, true);
        effect.setText("Prevent all combat damage that would be dealt this turn");
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new ManaCostsImpl<>("{2}"));
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(filter)));
        this.addAbility(ability);
    }

    private Sunstone(final Sunstone card) {
        super(card);
    }

    @Override
    public Sunstone copy() {
        return new Sunstone(this);
    }
}
