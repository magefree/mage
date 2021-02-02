
package mage.cards.a;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.PayEnergyCost;
import mage.abilities.effects.common.counter.GetEnergyCountersControllerEffect;
import mage.abilities.mana.AnyColorManaAbility;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author emerald000
 */
public final class AetherHub extends CardImpl {

    public AetherHub(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // When Aether Hub enters the battlefield, you get {E}.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GetEnergyCountersControllerEffect(1)));

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {T}, Pay {E}: Add one mana of any color.
        Ability ability = new AnyColorManaAbility();
        ability.addCost(new PayEnergyCost(1));
        this.addAbility(ability);
    }

    private AetherHub(final AetherHub card) {
        super(card);
    }

    @Override
    public AetherHub copy() {
        return new AetherHub(this);
    }
}
