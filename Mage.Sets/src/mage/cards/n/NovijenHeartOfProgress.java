
package mage.cards.n;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author someoneseth@gmail.com
 */
public final class NovijenHeartOfProgress extends CardImpl {

    public NovijenHeartOfProgress(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {G}{U}, {T}: Put a +1/+1 counter on each creature that entered the battlefield this turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new NovijenHeartOfProgressEffect(), new ManaCostsImpl<>("{G}{U}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private NovijenHeartOfProgress(final NovijenHeartOfProgress card) {
        super(card);
    }

    @Override
    public NovijenHeartOfProgress copy() {
        return new NovijenHeartOfProgress(this);
    }
}

class NovijenHeartOfProgressEffect extends OneShotEffect {

    public NovijenHeartOfProgressEffect() {
        super(Outcome.BoostCreature);
        staticText = "put a +1/+1 counter on each creature that entered the battlefield this turn";
    }

    public NovijenHeartOfProgressEffect(final NovijenHeartOfProgressEffect effect) {
        super(effect);
    }

    @Override
    public NovijenHeartOfProgressEffect copy() {
        return new NovijenHeartOfProgressEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source);
        if (controller != null && sourceObject != null) {
            for (Permanent permanent : game.getBattlefield().getActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, source.getControllerId(), game)) {
                if (permanent.getTurnsOnBattlefield() == 0) {
                    permanent.addCounters(CounterType.P1P1.createInstance(), source.getControllerId(), source, game);
                    game.informPlayers(sourceObject.getLogName() + ": " + controller.getLogName() + " puts a +1/+1 counter on " + permanent.getLogName());
                }
            }
            return true;
        }
        return false;
    }
}
