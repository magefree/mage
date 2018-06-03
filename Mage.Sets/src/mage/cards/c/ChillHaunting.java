
package mage.cards.c;

import java.util.UUID;
import mage.abilities.costs.common.ExileXFromYourGraveCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.dynamicvalue.common.SignInversionDynamicValue;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.common.FilterCreatureCard;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public final class ChillHaunting extends CardImpl {

    public ChillHaunting(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{B}");

        // As an additional cost to cast Chill Haunting, exile X creature cards from your graveyard.
        this.getSpellAbility().addCost(new ExileXFromYourGraveCost(new FilterCreatureCard("creature cards from your graveyard"), true));

        // Target creature gets -X/-X until end of turn.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        DynamicValue xval = new SignInversionDynamicValue(new GetXValue());
        this.getSpellAbility().addEffect(new BoostTargetEffect(xval, xval, Duration.EndOfTurn));
    }

    public ChillHaunting(final ChillHaunting card) {
        super(card);
    }

    @Override
    public ChillHaunting copy() {
        return new ChillHaunting(this);
    }
}
