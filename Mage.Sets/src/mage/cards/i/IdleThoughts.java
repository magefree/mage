
package mage.cards.i;

import java.util.UUID;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.common.CardsInHandCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;

/**
 *
 * @author jeffwadsworth
 */
public final class IdleThoughts extends CardImpl {

    public IdleThoughts(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{3}{U}");


        // {2}: Draw a card if you have no cards in hand.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new ConditionalOneShotEffect(
                new DrawCardSourceControllerEffect(2), new CardsInHandCondition(), "Draw a card if you have no cards in hand"), new ManaCostsImpl<>("{2}")));
    }

    private IdleThoughts(final IdleThoughts card) {
        super(card);
    }

    @Override
    public IdleThoughts copy() {
        return new IdleThoughts(this);
    }
}
