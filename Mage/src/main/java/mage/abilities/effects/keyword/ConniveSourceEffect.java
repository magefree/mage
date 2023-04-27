package mage.abilities.effects.keyword;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.util.CardUtil;

/**
 * @author TheElk801
 */
public class ConniveSourceEffect extends OneShotEffect {

    private final String selfName;
    private final ReflexiveTriggeredAbility ability;

    public ConniveSourceEffect() {
        this("it");
    }

    public ConniveSourceEffect(String selfName) {
        this(selfName, null);
    }

    public ConniveSourceEffect(String selfName, ReflexiveTriggeredAbility ability) {
        super(Outcome.Benefit);
        this.selfName = selfName;
        this.ability = ability;
    }

    private ConniveSourceEffect(final ConniveSourceEffect effect) {
        super(effect);
        this.selfName = effect.selfName;
        this.ability = effect.ability;
    }

    @Override
    public ConniveSourceEffect copy() {
        return new ConniveSourceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        boolean connived = connive(permanent, 1, source, game);
        if (ability != null) {
            game.fireReflexiveTriggeredAbility(ability, source);
        }
        return connived || ability != null;
    }

    public static boolean connive(Permanent permanent, int amount, Ability source, Game game) {
        if (amount < 1) {
            return false;
        }
        boolean permanentStillOnBattlefield;
        if (permanent == null) {
            // If the permanent was killed, get last known information
            permanent = (Permanent) game.getLastKnownInformation(source.getSourceId(), Zone.BATTLEFIELD);
            permanentStillOnBattlefield = false;
        } else {
            permanentStillOnBattlefield = true;
        }

        Player player = game.getPlayer(permanent.getControllerId());
        if (player == null) {
            return false;
        }
        player.drawCards(amount, source, game);
        int counters = player
                .discard(amount, false, false, source, game)
                .count(StaticFilters.FILTER_CARDS_NON_LAND, game);
        if (permanentStillOnBattlefield && counters > 0) {
            permanent.addCounters(CounterType.P1P1.createInstance(counters), source, game);
        }
        return true;
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        if (ability == null) {
            return selfName + " connives. <i>(Draw a card, then discard a card. " +
                    "If you discarded a nonland card, put a +1/+1 counter on this creature.)</i>";
        }
        return selfName + " connives. When it connives this way, " +
                CardUtil.getTextWithFirstCharLowerCase(ability.getRule()) +
                " <i>(To have a creature connive, draw a card, then discard a card. " +
                "If you discarded a nonland card, put a +1/+1 counter on that creature.)</i>";
    }
}
