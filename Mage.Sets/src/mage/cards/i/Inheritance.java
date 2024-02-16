
package mage.cards.i;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author Plopman
 */
public final class Inheritance extends CardImpl {

    public Inheritance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{W}");

        // Whenever a creature dies, you may pay {3}. If you do, draw a card.
        Ability ability = new DiesCreatureTriggeredAbility(new DoIfCostPaid(new DrawCardSourceControllerEffect(1), new ManaCostsImpl<>("{3}")), false);
        this.addAbility(ability);
    }

    private Inheritance(final Inheritance card) {
        super(card);
    }

    @Override
    public Inheritance copy() {
        return new Inheritance(this);
    }
}
