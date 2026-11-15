package mage.abilities.mana;

import mage.abilities.costs.Cost;
import mage.abilities.effects.mana.ManaEffect;
import mage.abilities.keyword.ExhaustAbility;
import mage.constants.Zone;
import mage.game.Game;

/**
 * 702.177a Exhaust for a mana ability (605.1a: no stack, can't be responded to), e.g. Loot, the Pathfinder.
 * Exhaust abilities without mana use ExhaustAbility.
 *
 * @author JayDi85
 */
public class ExhaustManaAbility extends ActivatedManaAbilityImpl {

    private final boolean withReminderText;

    public ExhaustManaAbility(ManaEffect effect, Cost cost) {
        this(effect, cost, true);
    }

    public ExhaustManaAbility(ManaEffect effect, Cost cost, boolean withReminderText) {
        super(Zone.BATTLEFIELD, effect, cost);
        this.withReminderText = withReminderText;
    }

    private ExhaustManaAbility(final ExhaustManaAbility ability) {
        super(ability);
        this.maxActivationsPerGame = 1;
        this.withReminderText = ability.withReminderText;
    }

    @Override
    public ExhaustManaAbility copy() {
        return new ExhaustManaAbility(this);
    }

    @Override
    public boolean hasMoreActivationsThisTurn(Game game) {
        ActivationInfo info = getActivationInfo(game);
        if (info != null && info.totalActivations >= getMaxActivationsPerGame(game)
                && ExhaustAbility.canActivateAsThoughNotActivated(this, game)) {
            return true;
        }
        return super.hasMoreActivationsThisTurn(game);
    }

    @Override
    public String getRule() {
        return "Exhaust &mdash; " + super.getRule() +
                (withReminderText ? " <i>(Activate each exhaust ability only once.)</i>" : "");
    }
}
