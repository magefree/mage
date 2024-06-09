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
 * 701.47. Connive
 * <p>
 * 701.47a Certain abilities instruct a permanent to connive. To do so, that permanent’s controller draws a card,
 * then discards a card. If a nonland card is discarded this way, that player puts a +1/+1 counter on the
 * conniving permanent.
 * <p>
 * 701.47b A permanent “connives” after the process described in rule 701.47a is complete, even if some or
 * all of those actions were impossible.
 * <p>
 * 701.47c If a permanent changes zones before an effect causes it to connive, its last known information is
 * used to determine which object connived and who controlled it.
 * <p>
 * 701.47d If multiple permanents are instructed to connive at the same time, the first player in APNAP order
 * who controls one or more of those permanents chooses one of them and it connives. Then if any permanents
 * remain on the battlefield which have been instructed to connive and have not done so, this process is repeated.
 * <p>
 * 701.47e Connive N is a variant of connive. The permanent’s controller draws N cards, discards N cards, then
 * puts a number of +1/+1 counters on the permanent equal to the number of nonland cards discarded this way.
 *
 * @author TheElk801
 */
public class ConniveSourceEffect extends OneShotEffect {

    private final String selfName;
    private final ReflexiveTriggeredAbility ability; // apply ability after connived

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
        // 701.47c If a permanent changes zones before an effect causes it to connive,
        // its last known information is used to determine which object connived and who controlled it.
        Permanent permanent = source.getSourcePermanentOrLKI(game);
        boolean connived = connive(permanent, 1, source, game);
        if (ability != null && connived) {
            game.fireReflexiveTriggeredAbility(ability, source);
        }
        return connived;
    }

    /**
     * @param permanent must use game.getPermanentOrLKIBattlefield in parent method due rules
     * @param amount
     * @param source
     * @param game
     * @return
     */
    public static boolean connive(Permanent permanent, int amount, Ability source, Game game) {
        if (amount < 1) {
            return false;
        }
        if (permanent == null) {
            return false;
        }

        boolean permanentStillOnBattlefield = game.getState().getZone(permanent.getId()) == Zone.BATTLEFIELD;
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
