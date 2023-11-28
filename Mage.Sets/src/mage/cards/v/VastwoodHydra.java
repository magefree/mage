
package mage.cards.v;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.EntersBattlefieldWithXCountersEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanentAmount;
import mage.target.common.TargetPermanentAmount;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class VastwoodHydra extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creatures you control");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public VastwoodHydra(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{X}{G}{G}");
        this.subtype.add(SubType.HYDRA);

        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Vastwood Hydra enters the battlefield with X +1/+1 counters on it.
        this.addAbility(new EntersBattlefieldAbility(new EntersBattlefieldWithXCountersEffect(CounterType.P1P1.createInstance())));

        // When Vastwood Hydra dies, you may distribute a number of +1/+1 counters equal to the number of +1/+1 counters on Vastwood Hydra among any number of creatures you control.
        this.addAbility(new DiesSourceTriggeredAbility(new VastwoodHydraDistributeEffect(), true));
    }

    private VastwoodHydra(final VastwoodHydra card) {
        super(card);
    }

    @Override
    public VastwoodHydra copy() {
        return new VastwoodHydra(this);
    }
}

class VastwoodHydraDistributeEffect extends OneShotEffect {

    public VastwoodHydraDistributeEffect() {
        super(Outcome.BoostCreature);
        this.staticText = "distribute a number of +1/+1 counters equal to the number of +1/+1 counters on {this} among any number of creatures you control";
    }

    private VastwoodHydraDistributeEffect(final VastwoodHydraDistributeEffect effect) {
        super(effect);
    }

    @Override
    public VastwoodHydraDistributeEffect copy() {
        return new VastwoodHydraDistributeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int amount = new CountersSourceCount(CounterType.P1P1).calculate(game, source, this);
        if (amount <= 0) {
            return false;
        }

        Player player = game.getPlayer(source.getControllerId());
        if (player == null || game.getBattlefield().count(StaticFilters.FILTER_CONTROLLED_CREATURE, player.getId(), source, game) < 1) {
            return false;
        }

        TargetPermanentAmount target = new TargetCreaturePermanentAmount(amount, StaticFilters.FILTER_CONTROLLED_CREATURE);
        target.setMinNumberOfTargets(1);
        target.withNotTarget(true);
        target.withChooseHint("to distribute " + amount + " counters");
        target.chooseTarget(outcome, player.getId(), source, game);
        for (UUID targetId : target.getTargets()) {
            Permanent permanent = game.getPermanent(targetId);
            if (permanent != null) {
                permanent.addCounters(CounterType.P1P1.createInstance(target.getTargetAmount(targetId)), source, game);
            }
        }
        return true;
    }

}
