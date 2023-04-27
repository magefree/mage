
package mage.cards.f;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;

/**
 *
 * @author LevelX2
 */
public final class FontOfFortunes extends CardImpl {

    public FontOfFortunes(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{U}");


        // {1}{U}, Sacrifice Font of Fortunes: Draw two cards.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(2), new ManaCostsImpl<>("{1}{U}"));
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);        
    }

    private FontOfFortunes(final FontOfFortunes card) {
        super(card);
    }

    @Override
    public FontOfFortunes copy() {
        return new FontOfFortunes(this);
    }
}
