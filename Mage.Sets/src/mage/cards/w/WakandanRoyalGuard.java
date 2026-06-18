package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;

/**
 *
 * @author muz
 */
public final class WakandanRoyalGuard extends CardImpl {

    public WakandanRoyalGuard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // When this creature enters, put a +1/+1 counter on target creature. If that creature is another Hero, put two +1/+1 counters on it instead.
        Ability ability = new EntersBattlefieldTriggeredAbility(new WakandanRoyalGuardEffect());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private WakandanRoyalGuard(final WakandanRoyalGuard card) {
        super(card);
    }

    @Override
    public WakandanRoyalGuard copy() {
        return new WakandanRoyalGuard(this);
    }
}

class WakandanRoyalGuardEffect extends OneShotEffect {

    public WakandanRoyalGuardEffect() {
        super(Outcome.Benefit);
        staticText = "put a +1/+1 counter on target creature. If that creature is another Hero, put two +1/+1 counters on it instead";
    }

    private WakandanRoyalGuardEffect(final WakandanRoyalGuardEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent != null) {
            if (permanent.getId() != source.getSourceId() && permanent.hasSubtype(SubType.HERO, game)) {
                permanent.addCounters(CounterType.P1P1.createInstance(2), source.getControllerId(), source, game);
            } else {
                permanent.addCounters(CounterType.P1P1.createInstance(), source.getControllerId(), source, game);
            }
            return true;
        }
        return false;
    }

    @Override
    public WakandanRoyalGuardEffect copy() {
        return new WakandanRoyalGuardEffect(this);
    }

}
