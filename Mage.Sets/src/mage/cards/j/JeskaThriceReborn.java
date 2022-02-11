package mage.cards.j;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.CanBeYourCommanderAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.GetXLoyaltyValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.PartnerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.DamagePlayerEvent;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.common.TargetAnyTarget;
import mage.target.common.TargetCreaturePermanent;
import mage.util.CardUtil;
import mage.watchers.common.CommanderPlaysCountWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class JeskaThriceReborn extends CardImpl {

    public JeskaThriceReborn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{2}{R}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.JESKA);
        this.setStartingLoyalty(0);

        // Jeska, Thrice Reborn enters the battlefield with a loyalty counter on it for each time you've cast a commander from the command zone this game.
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(
                CounterType.LOYALTY.createInstance(0), JeskaThriceRebornValue.instance, false
        ), "with a loyalty counter on her for each time " +
                "you've cast a commander from the command zone this game"
        ).addHint(JeskaThriceRebornValue.getHint()));

        // +0: Choose target creature. Until your next turn, if that creature would deal combat damage to one of your opponents, it deals triple that damage to that player instead.
        Ability ability = new LoyaltyAbility(new JeskaThriceRebornEffect(), 0);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // −X: Jeska, Thrice Reborn deals X damage to each of up to three targets.
        ability = new LoyaltyAbility(new DamageTargetEffect(GetXLoyaltyValue.instance)
                .setText("{this} deals X damage to each of up to three targets"));
        ability.addTarget(new TargetAnyTarget(0, 3));
        this.addAbility(ability);

        // Jeska, Thrice Reborn can be your commander.
        this.addAbility(CanBeYourCommanderAbility.getInstance());

        // Partner
        this.addAbility(PartnerAbility.getInstance());
    }

    private JeskaThriceReborn(final JeskaThriceReborn card) {
        super(card);
    }

    @Override
    public JeskaThriceReborn copy() {
        return new JeskaThriceReborn(this);
    }
}

enum JeskaThriceRebornValue implements DynamicValue {
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
    public JeskaThriceRebornValue copy() {
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

class JeskaThriceRebornEffect extends ReplacementEffectImpl {

    JeskaThriceRebornEffect() {
        super(Duration.UntilYourNextTurn, Outcome.Damage);
        staticText = "choose target creature. Until your next turn, " +
                "if that creature would deal combat damage to one of your opponents, " +
                "it deals triple that damage to that player instead";
    }

    private JeskaThriceRebornEffect(final JeskaThriceRebornEffect effect) {
        super(effect);
    }

    @Override
    public JeskaThriceRebornEffect copy() {
        return new JeskaThriceRebornEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGE_PLAYER;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (!((DamagePlayerEvent) event).isCombatDamage()
                || !event.getSourceId().equals(targetPointer.getFirst(game, source))) {
            return false;
        }
        Player player = game.getPlayer(source.getControllerId());
        return player != null && player.hasOpponent(event.getTargetId(), game);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        int amount = event.getAmount();
        event.setAmount(CardUtil.overflowInc(amount, event.getAmount()));
        event.setAmount(CardUtil.overflowInc(amount, event.getAmount()));
        return false;
    }
}
