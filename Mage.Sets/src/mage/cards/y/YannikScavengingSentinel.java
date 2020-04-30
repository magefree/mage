package mage.cards.y;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.delayed.OnLeaveReturnExiledToBattlefieldAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.SendOptionUsedEventEffect;
import mage.abilities.effects.common.counter.DistributeCountersEffect;
import mage.abilities.keyword.PartnerWithAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanentAmount;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class YannikScavengingSentinel extends CardImpl {

    public YannikScavengingSentinel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{W}");

        this.addSuperType(SuperType.LEGENDARY);
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

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("another creature you control");

    static {
        filter.add(AnotherPredicate.instance);
    }

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
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        if (player == null || sourcePermanent == null
                || game.getBattlefield().count(filter, source.getSourceId(), source.getControllerId(), game) < 1) {
            return false;
        }
        TargetPermanent target = new TargetPermanent(filter);
        target.setNotTarget(true);
        player.choose(outcome, target, source.getSourceId(), game);
        Permanent permanent = game.getPermanent(target.getFirstTarget());
        if (permanent == null) {
            return false;
        }
        int power = permanent.getPower().getValue();
        new ExileTargetEffect(CardUtil.getExileZoneId(
                game, source.getSourceId(), source.getSourceObjectZoneChangeCounter()
        ), permanent.getIdName()).apply(game, source);
        game.addDelayedTriggeredAbility(new OnLeaveReturnExiledToBattlefieldAbility(), source);
        if (game.getState().getZone(permanent.getId()) == Zone.EXILED) {
            game.addDelayedTriggeredAbility(new YannikScavengingSentinelReflexiveTriggeredAbility(power), source);
            return new SendOptionUsedEventEffect().apply(game, source);
        }
        return true;
    }
}

class YannikScavengingSentinelReflexiveTriggeredAbility extends DelayedTriggeredAbility {

    YannikScavengingSentinelReflexiveTriggeredAbility(int power) {
        super(new DistributeCountersEffect(CounterType.P1P1, power, false, ""), Duration.OneUse, true);
        this.addTarget(new TargetCreaturePermanentAmount(power));
    }

    private YannikScavengingSentinelReflexiveTriggeredAbility(final YannikScavengingSentinelReflexiveTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public YannikScavengingSentinelReflexiveTriggeredAbility copy() {
        return new YannikScavengingSentinelReflexiveTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.OPTION_USED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getPlayerId().equals(this.getControllerId())
                && event.getSourceId().equals(this.getSourceId());
    }

    @Override
    public String getRule() {
        return "Distribute X +1/+1 counters among any number of target creatures, " +
                "where X is the exiled creature's power.";
    }
}
