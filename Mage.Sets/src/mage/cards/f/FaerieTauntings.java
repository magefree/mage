
package mage.cards.f;

import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.condition.common.OnOpponentsTurnCondition;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 *
 * @author Styxo
 */
public final class FaerieTauntings extends CardImpl {

    public FaerieTauntings(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.TRIBAL,CardType.ENCHANTMENT},"{2}{B}");
        this.subtype.add(SubType.FAERIE);

        // Whenever you cast a spell during an opponent's turn, you may have each opponent lose 1 life
        this.addAbility(new ConditionalTriggeredAbility(new SpellCastControllerTriggeredAbility(new LoseLifeOpponentsEffect(1), true), OnOpponentsTurnCondition.instance,
                "Whenever you cast a spell during an opponent's turn, you may have each opponent lose 1 life."));

    }

    private FaerieTauntings(final FaerieTauntings card) {
        super(card);
    }

    @Override
    public FaerieTauntings copy() {
        return new FaerieTauntings(this);
    }
}