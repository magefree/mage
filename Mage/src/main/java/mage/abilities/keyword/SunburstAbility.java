
package mage.abilities.keyword;

import java.util.ArrayList;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.SunburstCount;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.counters.Counter;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author Plopman
 */
public class SunburstAbility extends EntersBattlefieldAbility {

    private static final String ruleCreature = "Sunburst <i>(This enters the battlefield with a +1/+1 counter on it for each color of mana spent to cast it.)</i>";
    private static final String ruleNonCreature = "Sunburst <i>(This enters the battlefield with a charge counter on it for each color of mana spent to cast it.)</i>";
    private boolean isCreature;

    public SunburstAbility(Card card) {
        super(new SunburstEffect(), "");
        isCreature = card.isCreature();
    }

    public SunburstAbility(final SunburstAbility ability) {
        super(ability);
        this.isCreature = ability.isCreature;
    }

    @Override
    public EntersBattlefieldAbility copy() {
        return new SunburstAbility(this);
    }

    @Override
    public String getRule() {
        return isCreature ? ruleCreature : ruleNonCreature;
    }

}

class SunburstEffect extends OneShotEffect {

    private static final DynamicValue amount = SunburstCount.instance;

    public SunburstEffect() {
        super(Outcome.Benefit);
        staticText = "Sunburst";
    }

    public SunburstEffect(final SunburstEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanentEntering(source.getSourceId());
        if (permanent != null) {
            Counter counter;
            if (permanent.isCreature()) {
                counter = CounterType.P1P1.createInstance(amount.calculate(game, source, this));
            } else {
                counter = CounterType.CHARGE.createInstance(amount.calculate(game, source, this));
            }
            if (counter != null) {
                ArrayList<UUID> appliedEffects = (ArrayList<UUID>) this.getValue("appliedEffects"); // the basic event is the EntersBattlefieldEvent, so use already applied replacement effects from that event
                permanent.addCounters(counter, source, game, appliedEffects);
                if (!game.isSimulation()) {
                    Player player = game.getPlayer(source.getControllerId());
                    if (player != null) {
                        game.informPlayers(player.getLogName() + " puts " + counter.getCount() + ' ' + counter.getName() + " counter on " + permanent.getName());
                    }
                }
            }
        }
        return true;
    }

    @Override
    public SunburstEffect copy() {
        return new SunburstEffect(this);
    }

}
