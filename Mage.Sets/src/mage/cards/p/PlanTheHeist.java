package mage.cards.p;

import mage.abilities.condition.common.HellbentCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.abilities.keyword.PlotAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PlanTheHeist extends CardImpl {

    public PlanTheHeist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{U}{U}");

        // Surveil 3 if you have no cards in hand. Then draw three cards.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new SurveilEffect(3), HellbentCondition.instance,
                "surveil 3 if you have no cards in hand"
        ));
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(3).concatBy("Then"));

        // Plot {3}{U}
        this.addAbility(new PlotAbility("{3}{U}"));
    }

    private PlanTheHeist(final PlanTheHeist card) {
        super(card);
    }

    @Override
    public PlanTheHeist copy() {
        return new PlanTheHeist(this);
    }
}
