package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.game.Game;
import mage.watchers.common.PermanentsSacrificedWatcher;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class GoblinBlastRunner extends CardImpl {

    public GoblinBlastRunner(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}");
        this.subtype.add(SubType.GOBLIN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Goblin Blast-Runner gets +2/+0 and has menace as long as you sacrificed a permanent this turn.
        Ability ability = new SimpleStaticAbility(new ConditionalContinuousEffect(
                new BoostSourceEffect(2, 0, Duration.WhileOnBattlefield),
                GoblinBlastRunnerCondition.instance, "{this} gets +2/+0"
        ));
        ability.addEffect(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(
                        new MenaceAbility(false), Duration.WhileOnBattlefield
                ), GoblinBlastRunnerCondition.instance, "and has menace as long as you sacrificed a permanent this turn"
        ));
        this.addAbility(ability.addHint(GoblinBlastRunnerCondition.getHint()), new PermanentsSacrificedWatcher());
    }

    private GoblinBlastRunner(final GoblinBlastRunner card) {
        super(card);
    }

    @Override
    public GoblinBlastRunner copy() {
        return new GoblinBlastRunner(this);
    }
}

enum GoblinBlastRunnerCondition implements Condition {
    instance;
    private static final Hint hint = new ConditionHint(
            instance, "You've sacrificed a permanent this turn"
    );

    @Override
    public boolean apply(Game game, Ability source) {
        UUID player = source.getControllerId();
        PermanentsSacrificedWatcher watcher = game.getState().getWatcher(PermanentsSacrificedWatcher.class);
        if (watcher == null) {
            return false;
        }
        return watcher.getThisTurnSacrificedPermanents(player) != null;
    }

    public static Hint getHint() {
        return hint;
    }
}
