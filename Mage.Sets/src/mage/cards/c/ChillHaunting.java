
package mage.cards.c;

import mage.abilities.costs.common.ExileXFromYourGraveCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.dynamicvalue.common.SignInversionDynamicValue;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ChillHaunting extends CardImpl {

    private static final DynamicValue xval = new SignInversionDynamicValue(GetXValue.instance);

    public ChillHaunting(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{B}");

        // As an additional cost to cast Chill Haunting, exile X creature cards from your graveyard.
        this.getSpellAbility().addCost(new ExileXFromYourGraveCost(StaticFilters.FILTER_CARD_CREATURES_YOUR_GRAVEYARD, true));

        // Target creature gets -X/-X until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(xval, xval, Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private ChillHaunting(final ChillHaunting card) {
        super(card);
    }

    @Override
    public ChillHaunting copy() {
        return new ChillHaunting(this);
    }
}
