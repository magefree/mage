package mage.cards.c;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.CommanderCastFromCommandZoneValue;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CommandersInsignia extends CardImpl {

    public CommandersInsignia(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}{W}");

        // Creatures you control get +1/+1 for each time you've cast your commander from the command zone this game.
        this.addAbility(new SimpleStaticAbility(
                new BoostControlledEffect(
                        CommanderCastFromCommandZoneValue.instance, CommanderCastFromCommandZoneValue.instance, Duration.WhileOnBattlefield
                ).setText("Creatures you control get +1/+1 for each time you've cast your commander from the command zone this game.")
        ).addHint(CommanderCastFromCommandZoneValue.getHint()));
    }

    private CommandersInsignia(final CommandersInsignia card) {
        super(card);
    }

    @Override
    public CommandersInsignia copy() {
        return new CommandersInsignia(this);
    }
}