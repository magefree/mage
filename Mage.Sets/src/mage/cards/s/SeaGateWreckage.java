
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.condition.common.HellbentCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;

/**
 *
 * @author fireshoes
 */
public final class SeaGateWreckage extends CardImpl {

    public SeaGateWreckage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());
        
        // {2}{C}, {T}: Draw a card. Activate this ability only if you have no cards in hand.
        Ability ability = new ConditionalActivatedAbility(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1),
            new ManaCostsImpl<>("{2}{C}"), HellbentCondition.instance);
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private SeaGateWreckage(final SeaGateWreckage card) {
        super(card);
    }

    @Override
    public SeaGateWreckage copy() {
        return new SeaGateWreckage(this);
    }
}
