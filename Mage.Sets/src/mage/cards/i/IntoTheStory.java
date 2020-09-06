package mage.cards.i;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.CardsInOpponentGraveyardCondition;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class IntoTheStory extends CardImpl {

    public IntoTheStory(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{5}{U}{U}");

        // This spell costs {3} less to cast if an opponent has seven or more cards in their graveyard.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL, new SpellCostReductionSourceEffect(3, CardsInOpponentGraveyardCondition.SEVEN)
        ).setRuleAtTheTop(true).addHint(CardsInOpponentGraveyardCondition.SEVEN.getHint()));

        // Draw four cards.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(4));
    }

    private IntoTheStory(final IntoTheStory card) {
        super(card);
    }

    @Override
    public IntoTheStory copy() {
        return new IntoTheStory(this);
    }
}
