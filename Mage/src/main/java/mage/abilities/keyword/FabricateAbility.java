
package mage.abilities.keyword;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.token.ServoToken;
import mage.players.Player;
import mage.util.CardUtil;

/**
 * @author emerald000
 */
public class FabricateAbility extends EntersBattlefieldTriggeredAbility {

    private final int value;

    public FabricateAbility(int value) {
        super(new FabricateEffect(value), false, true);
        this.value = value;
    }

    public FabricateAbility(final FabricateAbility ability) {
        super(ability);
        this.value = ability.value;
    }

    @Override
    public FabricateAbility copy() {
        return new FabricateAbility(this);
    }

    @Override
    public String getRule() {
        return "Fabricate " + value + " <i>(When this creature enters the battlefield, put "
                + CardUtil.numberToText(value, "a") + " +1/+1 counter" + (value > 1 ? "s" : "")
                + " on it or create " + CardUtil.numberToText(value, "a")
                + " 1/1 colorless Servo artifact creature token" + (value > 1 ? "s" : "") + ".)</i>";
    }
}

class FabricateEffect extends OneShotEffect {

    private final int value;

    FabricateEffect(int value) {
        super(Outcome.Benefit);
        this.value = value;
    }

    FabricateEffect(final FabricateEffect effect) {
        super(effect);
        this.value = effect.value;
    }

    @Override
    public FabricateEffect copy() {
        return new FabricateEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            MageObject sourceObject = source.getSourceObjectIfItStillExists(game);
            if (sourceObject != null && controller.chooseUse(
                    Outcome.BoostCreature,
                    "Fabricate " + value,
                    null,
                    "Put " + CardUtil.numberToText(value, "a") + " +1/+1 counter" + (value > 1 ? "s" : ""),
                    "Create " + CardUtil.numberToText(value, "a") + " 1/1 token" + (value > 1 ? "s" : ""),
                    source,
                    game)) {
                ((Card) sourceObject).addCounters(CounterType.P1P1.createInstance(value), source.getControllerId(), source, game);
            } else {
                new ServoToken().putOntoBattlefield(value, game, source, controller.getId());
            }
            return true;
        }
        return false;
    }
}
