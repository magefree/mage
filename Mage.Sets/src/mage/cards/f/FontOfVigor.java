
package mage.cards.f;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;

/**
 *
 * @author LevelX2
 */
public final class FontOfVigor extends CardImpl {

    public FontOfVigor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{W}");


        // {2}{W}, Sacrifice Font of Vigor: You gain 7 life.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainLifeEffect(7), new ManaCostsImpl<>("{2}{W}"));
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);        
    }

    private FontOfVigor(final FontOfVigor card) {
        super(card);
    }

    @Override
    public FontOfVigor copy() {
        return new FontOfVigor(this);
    }
}
