
package mage.cards.t;

import java.util.UUID;
import mage.Mana;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.DamageControllerEffect;
import mage.abilities.mana.ActivatedManaAbilityImpl;
import mage.abilities.mana.AnyColorManaAbility;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;

/**
 *
 * @author Plopman
 */
public final class TarnishedCitadel extends CardImpl {

    public TarnishedCitadel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // {tap}: Add {C}.
        this.addAbility(new SimpleManaAbility(Zone.BATTLEFIELD, Mana.ColorlessMana(1), new TapSourceCost()));
        // {tap}: Add one mana of any color. Tarnished Citadel deals 3 damage to you.
        ActivatedManaAbilityImpl ability = new AnyColorManaAbility(new TapSourceCost());
        ability.addEffect(new DamageControllerEffect(3));
        this.addAbility(ability);
    }

    private TarnishedCitadel(final TarnishedCitadel card) {
        super(card);
    }

    @Override
    public TarnishedCitadel copy() {
        return new TarnishedCitadel(this);
    }
}
