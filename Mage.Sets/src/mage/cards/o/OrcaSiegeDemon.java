package mage.cards.o;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetAnyTargetAmount;
import mage.target.targetadjustment.TargetAdjuster;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class OrcaSiegeDemon extends CardImpl {

    public OrcaSiegeDemon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{B}{R}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.DEMON);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever another creature dies, put a +1/+1 counter on Orca, Siege Demon.
        this.addAbility(new DiesCreatureTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance()), false, true));

        // When Orca dies, it deals damage equal to its power divided as you choose among any number of targets.
        Ability ability = new DiesSourceTriggeredAbility(new OrcaSiegeDemonEffect());
        ability.setTargetAdjuster(OrcaSiegeDemonAdjuster.instance);
        this.addAbility(ability);
    }

    private OrcaSiegeDemon(final OrcaSiegeDemon card) {
        super(card);
    }

    @Override
    public OrcaSiegeDemon copy() {
        return new OrcaSiegeDemon(this);
    }
}

enum OrcaSiegeDemonAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(ability.getSourceId());
        if (sourcePermanent != null) {
            ability.getTargets().clear();
            ability.addTarget(new TargetAnyTargetAmount(sourcePermanent.getPower().getValue()));
        }
    }
}

class OrcaSiegeDemonEffect extends OneShotEffect {

    public OrcaSiegeDemonEffect() {
        super(Outcome.Damage);
        this.staticText = "it deals damage equal to its power divided " +
                "as you choose among any number of targets";
    }

    public OrcaSiegeDemonEffect(final OrcaSiegeDemonEffect effect) {
        super(effect);
    }

    @Override
    public OrcaSiegeDemonEffect copy() {
        return new OrcaSiegeDemonEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (!source.getTargets().isEmpty()) {
            Target multiTarget = source.getTargets().get(0);
            for (UUID target : multiTarget.getTargets()) {
                Permanent permanent = game.getPermanent(target);
                if (permanent != null) {
                    permanent.damage(multiTarget.getTargetAmount(target), source.getSourceId(), source, game, false, true);
                } else {
                    Player player = game.getPlayer(target);
                    if (player != null) {
                        player.damage(multiTarget.getTargetAmount(target), source.getSourceId(), source, game, false, true);
                    }
                }
            }
            return true;
        }
        return false;
    }
}