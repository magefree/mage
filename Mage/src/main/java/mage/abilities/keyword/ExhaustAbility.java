package mage.abilities.keyword;

import mage.abilities.ActivatedAbilityImpl;
import mage.abilities.costs.Cost;
import mage.abilities.effects.Effect;
import mage.abilities.meta.ExhaustMetaAbility;
import mage.constants.TimingRule;
import mage.constants.Zone;
import mage.game.Game;

/**
 * @author TheElk801
 */
public class ExhaustAbility extends ActivatedAbilityImpl implements ExhaustMetaAbility {

    private final boolean withReminderText;

    public ExhaustAbility(Effect effect, Cost cost) {
        this(effect, cost, true);
    }

    public ExhaustAbility(Effect effect, Cost cost, boolean withReminderText) {
        this(effect, cost, withReminderText, false);
    }

    public ExhaustAbility(Effect effect, Cost cost, boolean withReminderText, boolean activateAsSorcery) {
        super(Zone.BATTLEFIELD, effect, cost);
        this.withReminderText = withReminderText;
        timing = activateAsSorcery ? TimingRule.SORCERY : TimingRule.INSTANT;
    }

    private ExhaustAbility(final ExhaustAbility ability) {
        super(ability);
        this.maxActivationsPerGame = 1;
        this.withReminderText = ability.withReminderText;
    }

    @Override
    public ExhaustAbility copy() {
        return new ExhaustAbility(this);
    }

    @Override
    public boolean hasMoreActivationsThisTurn(Game game) {
        return this.hasMoreExhaustActivationsThisTurn(game) || super.hasMoreActivationsThisTurn(game);
    }

    @Override
    public String getRule() {
        return this.getExhaustRule(super.getRule(), this.withReminderText);
    }
}
