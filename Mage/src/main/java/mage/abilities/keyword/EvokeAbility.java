package mage.abilities.keyword;

import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.EvokedCondition;
import mage.abilities.costs.AlternativeSourceCostsImpl;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.constants.Duration;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 * @author LevelX2
 */
public class EvokeAbility extends AlternativeSourceCostsImpl {

    protected static final String EVOKE_KEYWORD = "Evoke";
    protected static final String REMINDER_TEXT = "You may cast this spell for its evoke cost. "
            + "If you do, it's sacrificed when it enters the battlefield.";

    public EvokeAbility(String manaString) {
        this(new ManaCostsImpl<>(manaString));
    }

    public EvokeAbility(Cost cost) {
        this(cost, EVOKE_KEYWORD);
    }

    protected EvokeAbility(Cost cost, String activationKey) {
        super(EVOKE_KEYWORD, REMINDER_TEXT, cost, activationKey);
        this.addSubAbility(new EntersBattlefieldTriggeredAbility(
                new SacrificeSourceEffect(true).setText("its controller sacrifices it")
        ).setTriggerPhrase("When this permanent enters, ").withInterveningIf(EvokedCondition.instance).setRuleVisible(false));
    }

    protected EvokeAbility(final EvokeAbility ability) {
        super(ability);
    }

    @Override
    public EvokeAbility copy() {
        return new EvokeAbility(this);
    }

    public static String getActivationKey() {
        return getActivationKey(EVOKE_KEYWORD);
    }

    // Printed evoke carries the ETB sub-ability added in the constructor. Evoke
    // granted while casting is registered only as an alternative cost, so that
    // sub-ability is not added to the card and must be represented by a delayed
    // trigger instead.
    protected void addEvokeTriggeredAbility(Game game, Ability source) {
        game.addDelayedTriggeredAbility(new EvokeTriggeredAbility(), source);
    }

    private static class EvokeTriggeredAbility extends DelayedTriggeredAbility {

        private EvokeTriggeredAbility() {
            super(new SacrificeSourceEffect(true).setText("its controller sacrifices it"), Duration.Custom, true);
            setTriggerPhrase("When this permanent enters, ");
        }

        private EvokeTriggeredAbility(final EvokeTriggeredAbility ability) {
            super(ability);
        }

        @Override
        public EvokeTriggeredAbility copy() {
            return new EvokeTriggeredAbility(this);
        }

        @Override
        public boolean checkEventType(GameEvent event, Game game) {
            return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
        }

        @Override
        public boolean checkTrigger(GameEvent event, Game game) {
            return event.getTargetId().equals(getSourceId());
        }

        @Override
        public boolean isInactive(Game game) {
            return super.isInactive(game)
                    || game.getStack().getSpell(getSourceId()) == null
                    && game.getPermanent(getSourceId()) == null;
        }
    }
}
