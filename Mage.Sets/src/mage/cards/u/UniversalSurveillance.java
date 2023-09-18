package mage.cards.u;

import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.ImproviseAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class UniversalSurveillance extends CardImpl {

    public UniversalSurveillance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{U}{U}{U}");

        // Improvise
        this.addAbility(new ImproviseAbility());

        // Draw X cards.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(ManacostVariableValue.REGULAR));
    }

    private UniversalSurveillance(final UniversalSurveillance card) {
        super(card);
    }

    @Override
    public UniversalSurveillance copy() {
        return new UniversalSurveillance(this);
    }
}
