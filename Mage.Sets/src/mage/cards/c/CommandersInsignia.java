package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.CommanderCardType;
import mage.constants.Duration;
import mage.game.Game;
import mage.players.Player;
import mage.watchers.common.CommanderPlaysCountWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CommandersInsignia extends CardImpl {

    public CommandersInsignia(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}{W}");

        // Creatures you control get +1/+1 for each time you've cast your commander from the command zone this game.
        this.addAbility(new SimpleStaticAbility(new BoostControlledEffect(
                        CommandersInsigniaValue.instance, CommandersInsigniaValue.instance, Duration.WhileOnBattlefield
                ).setText("Creatures you control get +1/+1 for each time you've cast your commander from the command zone this game.")));
    }

    private CommandersInsignia(final CommandersInsignia card) {
        super(card);
    }

    @Override
    public CommandersInsignia copy() {
        return new CommandersInsignia(this);
    }
}

enum CommandersInsigniaValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        Player player = game.getPlayer(sourceAbility.getControllerId());
        CommanderPlaysCountWatcher watcher = game.getState().getWatcher(CommanderPlaysCountWatcher.class);
        if (player == null || watcher == null) {
            return 0;
        }
        return game
                .getCommandersIds(player, CommanderCardType.COMMANDER_OR_OATHBREAKER, false)
                .stream()
                .mapToInt(watcher::getPlaysCount)
                .sum();
    }

    @Override
    public DynamicValue copy() {
        return instance;
    }

    @Override
    public String getMessage() {
        return "";
    }
}