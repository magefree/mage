
package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.watchers.common.PlayerLostLifeWatcher;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class TaintedSigil extends CardImpl {

    private static final String rule = "You gain life equal to the total life lost by all players this turn";

    public TaintedSigil(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{W}{B}");

        // {tap}, Sacrifice Tainted Sigil: You gain life equal to the total life lost by all players this turn.
        Ability ability = new SimpleActivatedAbility(
                Zone.BATTLEFIELD, new GainLifeEffect(AllPlayersLostLifeCount.instance, rule), new TapSourceCost()
        );
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);

    }

    private TaintedSigil(final TaintedSigil card) {
        super(card);
    }

    @Override
    public TaintedSigil copy() {
        return new TaintedSigil(this);
    }
}

enum AllPlayersLostLifeCount implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return this.calculate(game, sourceAbility.getControllerId());
    }

    public int calculate(Game game, UUID controllerId) {
        PlayerLostLifeWatcher watcher = game.getState().getWatcher(PlayerLostLifeWatcher.class);
        if (watcher != null) {
            int amountLifeLost = 0;
            for (UUID playerId : game.getState().getPlayersInRange(controllerId, game)) {
                amountLifeLost += watcher.getLifeLost(playerId);
            }
            return amountLifeLost;
        }
        return 0;
    }

    @Override
    public DynamicValue copy() {
        return instance;
    }

    @Override
    public String toString() {
        return "X";
    }

    @Override
    public String getMessage() {
        return "the total life lost by all players this turn";
    }
}
