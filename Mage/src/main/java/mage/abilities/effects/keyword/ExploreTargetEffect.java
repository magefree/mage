package mage.abilities.effects.keyword;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;

/**
 * @author LevelX2
 */
public class ExploreTargetEffect extends OneShotEffect {

    private static final String REMINDER_TEXT = ". <i>(Reveal the top card of your library. "
            + "Put that card into your hand if it's a land. Otherwise, put a +1/+1 counter on "
            + "that creature, then put the card back or put it into your graveyard.)</i>";

    private final boolean withReminderText;

    public ExploreTargetEffect() {
        this(true);
    }

    public ExploreTargetEffect(boolean withReminderText) {
        super(Outcome.Benefit);
        this.withReminderText = withReminderText;
    }

    protected ExploreTargetEffect(final ExploreTargetEffect effect) {
        super(effect);
        this.withReminderText = effect.withReminderText;
    }

    @Override
    public ExploreTargetEffect copy() {
        return new ExploreTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return ExploreSourceEffect.explorePermanent(game, getTargetPointer().getFirst(game, source), source, 1);
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        return getTargetPointer().describeTargets(mode.getTargets(), "it")
                + " explores" + (withReminderText ? REMINDER_TEXT : "");
    }

}
