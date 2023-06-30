package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.players.Player;
import mage.watchers.common.CommanderPlaysCountWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CaptainVargusWrath extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent(SubType.PIRATE, "");

    public CaptainVargusWrath(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ORC);
        this.subtype.add(SubType.PIRATE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever Captain Vargus Wrath attacks, Pirates you control get +1/+1 until end of turn for each time you've cast a commander from the command zone this game.
        this.addAbility(new AttacksTriggeredAbility(new BoostControlledEffect(
                CaptainVargusWrathValue.instance, CaptainVargusWrathValue.instance,
                Duration.EndOfTurn, filter, false, true
        ), false, "Whenever {this} attacks, Pirates you control get +1/+1 until end of turn " +
                "for each time you've cast a commander from the command zone this game."
        ).addHint(CaptainVargusWrathValue.getHint()));
    }

    private CaptainVargusWrath(final CaptainVargusWrath card) {
        super(card);
    }

    @Override
    public CaptainVargusWrath copy() {
        return new CaptainVargusWrath(this);
    }
}

enum CaptainVargusWrathValue implements DynamicValue {
    instance;
    private static final Hint hint = new ValueHint(
            "Number of times you've cast a commander this game", instance
    );

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        Player player = game.getPlayer(sourceAbility.getControllerId());
        if (player == null) {
            return 0;
        }
        CommanderPlaysCountWatcher watcher = game.getState().getWatcher(CommanderPlaysCountWatcher.class);
        return watcher == null ? 0 : game
                .getCommandersIds(player, CommanderCardType.COMMANDER_OR_OATHBREAKER, false)
                .stream()
                .mapToInt(watcher::getPlaysCount)
                .sum();
    }

    @Override
    public CaptainVargusWrathValue copy() {
        return instance;
    }

    @Override
    public String getMessage() {
        return "";
    }

    public static Hint getHint() {
        return hint;
    }
}
