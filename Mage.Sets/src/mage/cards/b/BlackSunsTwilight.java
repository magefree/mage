package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.dynamicvalue.common.SignInversionDynamicValue;
import mage.abilities.effects.OneShotNonTargetEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Duration;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetadjustment.XManaValueTargetAdjuster;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BlackSunsTwilight extends CardImpl {

    private static final DynamicValue xValue = new SignInversionDynamicValue(GetXValue.instance);

    public BlackSunsTwilight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{X}{B}");

        // Up to one target creature gets -X/-X until end of turn. If X is 5 or more, return a creature card with mana value X or less from your graveyard to the battlefield tapped.
        this.getSpellAbility().addEffect(new BoostTargetEffect(
                xValue, xValue, Duration.EndOfTurn
        ));
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new OneShotNonTargetEffect(
                        new ReturnFromGraveyardToBattlefieldTargetEffect(true)
                                .setText("return a creature card with mana value X or less from your graveyard to the battlefield tapped"),
                        new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD),
                        new XManaValueTargetAdjuster(ComparisonType.OR_LESS)
                ),
                BlackSunsTwilightCondition.instance,
                "If X is 5 or more, return a creature card with mana value " +
                        "X or less from your graveyard to the battlefield tapped"
        ));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(0, 1));
    }

    private BlackSunsTwilight(final BlackSunsTwilight card) {
        super(card);
    }

    @Override
    public BlackSunsTwilight copy() {
        return new BlackSunsTwilight(this);
    }
}

enum BlackSunsTwilightCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return CardUtil.getSourceCostsTag(game, source, "X", 0) >= 5;
    }
}
