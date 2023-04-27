package mage.cards.z;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author weirddan455
 */
public final class ZephyrSentinel extends CardImpl {

    private static final FilterControlledCreaturePermanent filter
            = new FilterControlledCreaturePermanent("other creature you control");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public ZephyrSentinel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Zephyr Sentinel enters the battlefield, return up to one other target creature you control to its owner's hand. If it was a Soldier, put a +1/+1 counter on Zephyr Sentinel.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ZephyrSentinelEffect());
        ability.addTarget(new TargetControlledCreaturePermanent(0, 1, filter, false));
        this.addAbility(ability);
    }

    private ZephyrSentinel(final ZephyrSentinel card) {
        super(card);
    }

    @Override
    public ZephyrSentinel copy() {
        return new ZephyrSentinel(this);
    }
}

class ZephyrSentinelEffect extends OneShotEffect {

    public ZephyrSentinelEffect() {
        super(Outcome.ReturnToHand);
        this.staticText = "return up to one other target creature you control to its owner's hand. If it was a Soldier, put a +1/+1 counter on {this}";
    }

    private ZephyrSentinelEffect(final ZephyrSentinelEffect effect) {
        super(effect);
    }

    @Override
    public ZephyrSentinelEffect copy() {
        return new ZephyrSentinelEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent targetPermanent = game.getPermanent(source.getFirstTarget());
        if (targetPermanent == null) {
            return false;
        }
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        boolean soldier = targetPermanent.hasSubtype(SubType.SOLDIER, game);
        controller.moveCards(targetPermanent, Zone.HAND, source, game);
        if (soldier) {
            Permanent sourcePermanent = source.getSourcePermanentIfItStillExists(game);
            if (sourcePermanent != null) {
                sourcePermanent.addCounters(CounterType.P1P1.createInstance(), source, game);
            }
        }
        return true;
    }
}
