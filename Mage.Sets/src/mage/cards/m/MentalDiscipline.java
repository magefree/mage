
package mage.cards.m;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;

/**
 *
 * @author LoneFox
 */
public final class MentalDiscipline extends CardImpl {

    public MentalDiscipline(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{U}{U}");

        // {1}{U}, Discard a card: Draw a card.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1), new ManaCostsImpl<>("{1}{U}"));
        ability.addCost(new DiscardCardCost());
        this.addAbility(ability);
    }

    private MentalDiscipline(final MentalDiscipline card) {
        super(card);
    }

    @Override
    public MentalDiscipline copy() {
        return new MentalDiscipline(this);
    }
}
