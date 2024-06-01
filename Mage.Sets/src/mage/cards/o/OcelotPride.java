package mage.cards.o;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.CitysBlessingCondition;
import mage.abilities.condition.common.YouGainedLifeCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.abilities.hint.common.CitysBlessingHint;
import mage.abilities.keyword.AscendAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.EnteredThisTurnPredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.CatToken3;
import mage.target.targetpointer.FixedTarget;
import mage.watchers.common.PlayerGainedLifeWatcher;

import java.util.List;
import java.util.UUID;

/**
 * @author Susucr
 */
public final class OcelotPride extends CardImpl {

    private static final Condition condition = new YouGainedLifeCondition(ComparisonType.MORE_THAN, 0);
    private static final Hint hint = new ConditionHint(condition, "You gained life this turn");

    public OcelotPride(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}");

        this.subtype.add(SubType.CAT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Ascend
        this.addAbility(new AscendAbility());

        // At the beginning of your end step, if you gained life this turn, create a 1/1 white Cat creature token. Then if you have the city's blessing, for each token you control that entered the battlefield this turn, create a token that's a copy of it.
        Ability ability = new BeginningOfEndStepTriggeredAbility(
                new CreateTokenEffect(new CatToken3()),
                TargetController.YOU, condition, false
        );
        ability.addEffect(new ConditionalOneShotEffect(
                new OcelotPrideEffect(),
                CitysBlessingCondition.instance
        ).concatBy("Then"));
        ability.addHint(hint);
        this.addAbility(ability.addHint(CitysBlessingHint.instance), new PlayerGainedLifeWatcher());
    }

    private OcelotPride(final OcelotPride card) {
        super(card);
    }

    @Override
    public OcelotPride copy() {
        return new OcelotPride(this);
    }
}

class OcelotPrideEffect extends OneShotEffect {

    private static final FilterControlledPermanent filter =
            new FilterControlledPermanent("token you control that entered the battlefield this turn");

    static {
        filter.add(TokenPredicate.TRUE);
        filter.add(EnteredThisTurnPredicate.instance);
    }

    OcelotPrideEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "for each token you control that entered the battlefield this turn, create a token that's a copy of it";
    }

    private OcelotPrideEffect(final OcelotPrideEffect effect) {
        super(effect);
    }

    @Override
    public OcelotPrideEffect copy() {
        return new OcelotPrideEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        boolean result = false;
        List<Permanent> tokensToCopy =
                game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source, game);
        for (Permanent toCopy : tokensToCopy) {
            result |= new CreateTokenCopyTargetEffect()
                    .setTargetPointer(new FixedTarget(toCopy, game))
                    .apply(game, source);
        }
        return result;
    }
}