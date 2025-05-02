package mage.cards.c;

import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;

import java.util.*;

/**
 * @author TheElk801
 */
public final class CallTheSpiritDragons extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.DRAGON, "Dragons");

    public CallTheSpiritDragons(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{W}{U}{B}{R}{G}");

        // Dragons you control have indestructible.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                IndestructibleAbility.getInstance(), Duration.WhileOnBattlefield, filter
        )));

        // At the beginning of your upkeep, for each color, put a +1/+1 counter on a Dragon you control of that color. If you put +1/+1 counters on five Dragons this way, you win the game.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new CallTheSpiritDragonsEffect()));
    }

    private CallTheSpiritDragons(final CallTheSpiritDragons card) {
        super(card);
    }

    @Override
    public CallTheSpiritDragons copy() {
        return new CallTheSpiritDragons(this);
    }
}

class CallTheSpiritDragonsEffect extends OneShotEffect {

    private static final List<FilterPermanent> filters = new ArrayList<>();

    static {
        for (ObjectColor color : ObjectColor.getAllColors()) {
            FilterPermanent filter = new FilterControlledPermanent(
                    SubType.DRAGON, color.getDescription() + " Dragon you control"
            );
            filter.add(new ColorPredicate(color));
            filters.add(filter);
        }
    }

    CallTheSpiritDragonsEffect() {
        super(Outcome.Benefit);
        staticText = "for each color, put a +1/+1 counter on a Dragon you control of that color. " +
                "If you put +1/+1 counters on five Dragons this way, you win the game";
    }

    private CallTheSpiritDragonsEffect(final CallTheSpiritDragonsEffect effect) {
        super(effect);
    }

    @Override
    public CallTheSpiritDragonsEffect copy() {
        return new CallTheSpiritDragonsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Set<UUID> dragons = new HashSet<>();
        for (FilterPermanent filter : filters) {
            if (!game.getBattlefield().contains(filter, source, game, 1)) {
                continue;
            }
            TargetPermanent target = new TargetPermanent(filter);
            target.withNotTarget(true);
            player.choose(Outcome.BoostCreature, target, source, game);
            Permanent permanent = game.getPermanent(target.getFirstTarget());
            if (permanent != null && permanent.addCounters(CounterType.P1P1.createInstance(), source, game)) {
                dragons.add(permanent.getId());
            }
        }
        if (dragons.size() >= 5) {
            player.won(game);
        }
        return !dragons.isEmpty();
    }
}
