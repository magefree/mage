
package mage.cards.f;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.condition.common.HellbentCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;

/**
 *
 * @author LoneFox
 */
public final class FoolsTome extends CardImpl {

    public FoolsTome(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{4}");

        // {2}, {tap}: Draw a card. Activate this ability only if you have no cards in hand.
        Ability ability = new ConditionalActivatedAbility(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1),
            new ManaCostsImpl<>("{2}"), HellbentCondition.instance);
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private FoolsTome(final FoolsTome card) {
        super(card);
    }

    @Override
    public FoolsTome copy() {
        return new FoolsTome(this);
    }
}
