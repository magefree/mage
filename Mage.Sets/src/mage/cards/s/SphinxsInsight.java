package mage.cards.s;

import mage.abilities.condition.common.AddendumCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SphinxsInsight extends CardImpl {

    public SphinxsInsight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{W}{U}");


        // Draw two cards.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(2));

        // Addendum — If you cast this spell during your main phase, you gain 2 life.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new GainLifeEffect(2), AddendumCondition.instance,
                "<br><i>Addendum</i> &mdash; If you cast this spell during your main phase, you gain 2 life."
        ));
    }

    private SphinxsInsight(final SphinxsInsight card) {
        super(card);
    }

    @Override
    public SphinxsInsight copy() {
        return new SphinxsInsight(this);
    }
}
