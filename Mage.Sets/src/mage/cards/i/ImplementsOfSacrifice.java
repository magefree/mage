
package mage.cards.i;

import java.util.UUID;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.mana.AddManaOfAnyColorEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;

/**
 *
 * @author fireshoes
 */
public final class ImplementsOfSacrifice extends CardImpl {

    public ImplementsOfSacrifice(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{2}");

        // {1}, {tap}, Sacrifice Implements of Sacrifice: Add two mana of any one color.
        SimpleManaAbility ability = new SimpleManaAbility(Zone.BATTLEFIELD, new AddManaOfAnyColorEffect(2), new ManaCostsImpl<>("{1}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private ImplementsOfSacrifice(final ImplementsOfSacrifice card) {
        super(card);
    }

    @Override
    public ImplementsOfSacrifice copy() {
        return new ImplementsOfSacrifice(this);
    }
}
