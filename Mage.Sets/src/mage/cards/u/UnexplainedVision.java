package mage.cards.u;

import mage.abilities.condition.common.AdamantCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class UnexplainedVision extends CardImpl {

    public UnexplainedVision(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{U}");

        // Draw three cards.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(3));

        // Adamant — If at least three blue mana was spent to cast this spell, scry 3.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new ScryEffect(3), AdamantCondition.BLUE, "<br><i>Adamant</i> &mdash; " +
                "If at least three blue mana was spent to cast this spell, scry 3."
        ));
    }

    private UnexplainedVision(final UnexplainedVision card) {
        super(card);
    }

    @Override
    public UnexplainedVision copy() {
        return new UnexplainedVision(this);
    }
}
