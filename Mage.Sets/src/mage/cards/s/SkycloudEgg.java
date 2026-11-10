
package mage.cards.s;

import java.util.UUID;
import mage.Mana;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.mana.BasicManaEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author fireshoes
 */
public final class SkycloudEgg extends CardImpl {

    public SkycloudEgg(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{1}");

        // {2}, {tap}, Sacrifice Skycloud Egg: Add {W}{U}. Draw a card.
        final SimpleActivatedAbility ability = new SimpleActivatedAbility(new BasicManaEffect(new Mana(1, 1, 0, 0, 0, 0, 0, 0)), new ManaCostsImpl<>("{2}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        ability.addEffect(new DrawCardSourceControllerEffect(1));
        this.addAbility(ability);
    }

    private SkycloudEgg(final SkycloudEgg card) {
        super(card);
    }

    @Override
    public SkycloudEgg copy() {
        return new SkycloudEgg(this);
    }
}
