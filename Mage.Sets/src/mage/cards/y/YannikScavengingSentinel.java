package mage.cards.y;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.delayed.OnLeaveReturnExiledAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.counter.DistributeCountersEffect;
import mage.abilities.keyword.PartnerWithAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanentAmount;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class YannikScavengingSentinel extends CardImpl {

    public YannikScavengingSentinel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HYENA);
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Partner with Nikara, Lair Scavenger
        this.addAbility(new PartnerWithAbility("Nikara, Lair Scavenger"));

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // When Yannik, Scavenging Sentinel enters the battlefield, exile another creature you control until Yannik leaves the battlefield. When you do, distribute X +1/+1 counters among any number of target creatures, where X is the exiled creature's power.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new YannikScavengingSentinelEffect()));
    }

    private YannikScavengingSentinel(final YannikScavengingSentinel card) {
        super(card);
    }

    @Override
    public YannikScavengingSentinel copy() {
        return new YannikScavengingSentinel(this);
    }
}

class YannikScavengingSentinelEffect extends OneShotEffect {

    YannikScavengingSentinelEffect() {
        super(Outcome.Benefit);
        staticText = "exile another creature you control until {this} leaves the battlefield. " +
                "When you do, distribute X +1/+1 counters among any number of target creatures, " +
                "where X is the exiled creature's power.";
    }

    private YannikScavengingSentinelEffect(final YannikScavengingSentinelEffect effect) {
        super(effect);
    }

    @Override
    public YannikScavengingSentinelEffect copy() {
        return new YannikScavengingSentinelEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent sourcePermanent = source.getSourcePermanentIfItStillExists(game);
        if (player == null || sourcePermanent == null
                || game.getBattlefield().count(StaticFilters.FILTER_ANOTHER_CREATURE_YOU_CONTROL, source.getControllerId(), source, game) < 1) {
            return false;
        }
        TargetPermanent target = new TargetPermanent(StaticFilters.FILTER_ANOTHER_CREATURE_YOU_CONTROL);
        target.setNotTarget(true);
        player.choose(outcome, target, source, game);
        Permanent permanent = game.getPermanent(target.getFirstTarget());
        if (permanent == null) {
            return false;
        }
        int power = permanent.getPower().getValue();
        new ExileTargetEffect(CardUtil.getExileZoneId(
                game, source.getSourceId(), source.getSourceObjectZoneChangeCounter()
        ), permanent.getIdName()).setTargetPointer(new FixedTarget(permanent, game)).apply(game, source);
        game.addDelayedTriggeredAbility(new OnLeaveReturnExiledAbility(), source);
        if (game.getState().getZone(permanent.getId()) != Zone.BATTLEFIELD) {
            ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(
                    new DistributeCountersEffect(
                            CounterType.P1P1, power, false, ""
                    ), false, "distribute X +1/+1 counters among any number of target creatures, " +
                    "where X is the exiled creature's power"
            );
            ability.addTarget(new TargetCreaturePermanentAmount(power));
            game.fireReflexiveTriggeredAbility(ability, source);
        }
        return true;
    }
}
