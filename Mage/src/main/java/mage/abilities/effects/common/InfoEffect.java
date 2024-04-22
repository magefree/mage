package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.hint.Hint;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 * @author BetaSteward_at_googlemail.com, JayDi85
 */
public class InfoEffect extends OneShotEffect {

    public InfoEffect(String text) {
        super(Outcome.Neutral);
        this.staticText = text;
    }

    protected InfoEffect(final InfoEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public InfoEffect copy() {
        return new InfoEffect(this);
    }

    public static void addInfoToPermanent(Game game, Ability source, Permanent permanent, String info) {
        addInfoToPermanent(game, source, permanent, info, Duration.WhileOnBattlefield);
    }

    /**
     * Add temporary information string to permanent (visible in rules list)
     *
     * @param game
     * @param source
     * @param permanent
     * @param info
     * @param duration
     */
    public static void addInfoToPermanent(Game game, Ability source, Permanent permanent, String info, Duration duration) {
        // add simple static info to permanent's rules
        SimpleStaticAbility ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new InfoEffect(info));

        GainAbilityTargetEffect gainEffect = new GainAbilityTargetEffect(ability, duration);
        gainEffect.setTargetPointer(new FixedTarget(permanent, game));
        game.addEffect(gainEffect, source);
    }

    /**
     * Add temporary card hint to permanent (visible in rules list)
     *
     * @param game
     * @param source
     * @param permanent
     * @param cardHint
     * @param duration
     */
    public static void addCardHintToPermanent(Game game, Ability source, Permanent permanent, Hint cardHint, Duration duration) {
        SimpleStaticAbility ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new InfoEffect("hint"));
        ability.setRuleVisible(false);
        ability.addHint(cardHint);

        GainAbilityTargetEffect gainEffect = new GainAbilityTargetEffect(ability, duration);
        gainEffect.setTargetPointer(new FixedTarget(permanent, game));
        game.addEffect(gainEffect, source);
    }

    /**
     * Add temporary card hint to permanent (visible in rules list)
     * Will be visible on conditional only
     *
     * @param game
     * @param source
     * @param permanent
     * @param cardHint
     * @param duration
     * @param condition
     */
    public static void addCardHintToPermanentConditional(Game game, Ability source, Permanent permanent, Hint cardHint, Duration duration, Condition condition) {
        SimpleStaticAbility ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new InfoEffect("hint"));
        ability.setRuleVisible(false);
        ability.addHint(cardHint);

        GainAbilityTargetEffect gainEffect = new GainAbilityTargetEffect(ability, duration);
        gainEffect.setTargetPointer(new FixedTarget(permanent, game));
        ConditionalContinuousEffect conditionalEffect = new ConditionalContinuousEffect(gainEffect, condition, "test");
        conditionalEffect.setTargetPointer(new FixedTarget(permanent, game));
        game.addEffect(conditionalEffect, source);
    }
}
