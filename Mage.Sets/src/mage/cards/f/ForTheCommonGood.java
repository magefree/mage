package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ForTheCommonGood extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent("token you control");

    static {
        filter.add(TokenPredicate.TRUE);
    }

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter);
    private static final Hint hint = new ValueHint("Tokens you control", xValue);

    public ForTheCommonGood(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{X}{G}");

        // Create X tokens that are copies of target token you control. Then tokens you control gain indestructible until your next turn. You gain 1 life for each token you control.
        this.getSpellAbility().addEffect(new ForTheCommonGoodEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
        this.getSpellAbility().addEffect(new GainAbilityControlledEffect(
                IndestructibleAbility.getInstance(),
                Duration.UntilYourNextTurn, StaticFilters.FILTER_PERMANENT_TOKENS
        ).concatBy("Then"));
        this.getSpellAbility().addEffect(new GainLifeEffect(xValue).setText("You gain 1 life for each token you control"));
        this.getSpellAbility().addHint(hint);
    }

    private ForTheCommonGood(final ForTheCommonGood card) {
        super(card);
    }

    @Override
    public ForTheCommonGood copy() {
        return new ForTheCommonGood(this);
    }
}

class ForTheCommonGoodEffect extends OneShotEffect {

    ForTheCommonGoodEffect() {
        super(Outcome.Benefit);
        staticText = "create X tokens that are copies of target token you control";
    }

    private ForTheCommonGoodEffect(final ForTheCommonGoodEffect effect) {
        super(effect);
    }

    @Override
    public ForTheCommonGoodEffect copy() {
        return new ForTheCommonGoodEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        int xValue = CardUtil.getSourceCostsTag(game, source, "X", 0);
        return permanent != null
                && xValue > 0
                && new CreateTokenCopyTargetEffect(null, null, false, xValue)
                .setSavedPermanent(permanent)
                .apply(game, source);
    }
}
