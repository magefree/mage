
package mage.cards.c;

import java.util.UUID;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.mana.AddManaOfAnyColorEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author Loki
 */
public final class ChromaticSphere extends CardImpl {

    public ChromaticSphere(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{1}");
        
        // {1}, {T}, Sacrifice Chromatic Sphere: Add one mana of any color. Draw a card.
        final SimpleActivatedAbility ability = new SimpleActivatedAbility(new AddManaOfAnyColorEffect(1), new GenericManaCost(1));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        ability.addEffect(new DrawCardSourceControllerEffect(1));
        this.addAbility(ability);
    }

    private ChromaticSphere(final ChromaticSphere card) {
        super(card);
    }

    @Override
    public ChromaticSphere copy() {
        return new ChromaticSphere(this);
    }
}
