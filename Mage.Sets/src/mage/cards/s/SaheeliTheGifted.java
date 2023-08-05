package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.SpellAbility;
import mage.abilities.common.CanBeYourCommanderAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.CostModificationType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.ServoToken;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;
import mage.watchers.common.CastSpellLastTurnWatcher;

/**
 *
 * @author TheElk801
 */
public final class SaheeliTheGifted extends CardImpl {

    public SaheeliTheGifted(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{2}{U}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SAHEELI);
        this.setStartingLoyalty(4);

        // +1: Create a 1/1 colorless Servo artifact creature token.
        this.addAbility(new LoyaltyAbility(
                new CreateTokenEffect(new ServoToken()), 1
        ));

        // +1: The next spell you cast this turn costs {1} less to cast for each artifact you control as you cast it.
        this.addAbility(new LoyaltyAbility(
                new SaheeliTheGiftedCostReductionEffect(), 1
        ));

        // -7: For each artifact you control, create a token that's a copy of it. Those tokens gain haste. Exile those tokens at the beginning of the next end step.
        this.addAbility(new LoyaltyAbility(
                new SaheeliTheGiftedTokenEffect(), -7
        ));

        // Saheeli, the Gifted can be your commander.
        this.addAbility(CanBeYourCommanderAbility.getInstance());
    }

    private SaheeliTheGifted(final SaheeliTheGifted card) {
        super(card);
    }

    @Override
    public SaheeliTheGifted copy() {
        return new SaheeliTheGifted(this);
    }
}

class SaheeliTheGiftedCostReductionEffect extends CostModificationEffectImpl {

    private int spellsCast;

    public SaheeliTheGiftedCostReductionEffect() {
        super(Duration.EndOfTurn, Outcome.Benefit, CostModificationType.REDUCE_COST);
        staticText = "the next spell you cast this turn costs {1} less to cast "
                + "for each artifact you control as you cast it";
    }

    protected SaheeliTheGiftedCostReductionEffect(final SaheeliTheGiftedCostReductionEffect effect) {
        super(effect);
        this.spellsCast = effect.spellsCast;
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        CastSpellLastTurnWatcher watcher = game.getState().getWatcher(CastSpellLastTurnWatcher.class);
        if (watcher != null) {
            spellsCast = watcher.getAmountOfSpellsPlayerCastOnCurrentTurn(source.getControllerId());
        }
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        int artifactCount = game.getBattlefield().getAllActivePermanents(
                StaticFilters.FILTER_PERMANENT_ARTIFACT_AN,
                source.getControllerId(), game
        ).size();
        CardUtil.reduceCost(abilityToModify, artifactCount);
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        CastSpellLastTurnWatcher watcher = game.getState().getWatcher(CastSpellLastTurnWatcher.class);
        if (watcher != null) {
            if (watcher.getAmountOfSpellsPlayerCastOnCurrentTurn(source.getControllerId()) > spellsCast) {
                discard(); // only one use 
                return false;
            }
        }
        if (abilityToModify instanceof SpellAbility) {
            return abilityToModify.isControlledBy(source.getControllerId());
        }
        return false;
    }

    @Override
    public SaheeliTheGiftedCostReductionEffect copy() {
        return new SaheeliTheGiftedCostReductionEffect(this);
    }
}

class SaheeliTheGiftedTokenEffect extends OneShotEffect {

    public SaheeliTheGiftedTokenEffect() {
        super(Outcome.Benefit);
        this.staticText = "for each artifact you control, "
                + "create a token that's a copy of it. "
                + "Those tokens gain haste. "
                + "Exile those tokens at the beginning of the next end step.";
    }

    public SaheeliTheGiftedTokenEffect(final SaheeliTheGiftedTokenEffect effect) {
        super(effect);
    }

    @Override
    public SaheeliTheGiftedTokenEffect copy() {
        return new SaheeliTheGiftedTokenEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(
                StaticFilters.FILTER_PERMANENT_ARTIFACT_AN,
                source.getControllerId(), game
        )) {
            if (permanent != null) {
                CreateTokenCopyTargetEffect effect
                        = new CreateTokenCopyTargetEffect();
                effect.setTargetPointer(new FixedTarget(permanent, game));
                effect.setHasHaste(true);
                effect.apply(game, source);
                effect.exileTokensCreatedAtNextEndStep(game, source);
            }
        }
        return true;
    }
}
