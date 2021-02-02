
package mage.cards.a;

import java.util.UUID;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.DamageControllerEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;

/**
 * @author Loki
 */
public final class AncientTomb extends CardImpl {

    public AncientTomb(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // {tap}: Add . Ancient Tomb deals 2 damage to you.
        Ability ability = new SimpleManaAbility(Zone.BATTLEFIELD, Mana.ColorlessMana(2), new TapSourceCost());
        ability.addEffect(new DamageControllerEffect(2));
        this.addAbility(ability);
    }

    private AncientTomb(final AncientTomb card) {
        super(card);
    }

    @Override
    public AncientTomb copy() {
        return new AncientTomb(this);
    }
}
