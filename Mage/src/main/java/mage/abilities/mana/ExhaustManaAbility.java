package mage.abilities.mana;

import mage.abilities.costs.Cost;
import mage.abilities.effects.mana.ManaEffect;
import mage.abilities.mana.ActivatedManaAbilityImpl;
import mage.abilities.meta.ExhaustMetaAbility;;
import mage.constants.Zone;
import mage.game.Game;

public class ExhaustManaAbility extends ActivatedManaAbilityImpl implements ExhaustMetaAbility {

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
        return this.hasMoreExhaustActivationsThisTurn(game) || super.hasMoreActivationsThisTurn(game);
    }

    @Override
    public String getRule() {
        return this.getExhaustRule(super.getRule(), this.withReminderText);
    }
}
