package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersPlayersEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class EzuriClawOfProgress extends CardImpl {

    final private static FilterControlledCreaturePermanent filter
            = new FilterControlledCreaturePermanent("a creature with power 2 or less");
    final private static FilterControlledCreaturePermanent filter2 = new FilterControlledCreaturePermanent();

    static {
        filter.add(new PowerPredicate(ComparisonType.FEWER_THAN, 3));
        filter2.add(AnotherPredicate.instance);
    }

    public EzuriClawOfProgress(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{U}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever a creature with power 2 or less enters the battlefield under your control, you get an experience counter.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(new AddCountersPlayersEffect(
                CounterType.EXPERIENCE.createInstance(), TargetController.YOU
        ), filter));

        // At the beginning of combat on your turn, put X +1/+1 counters on another target creature you control, where X is the number of experience counters you have.
        Ability ability = new BeginningOfCombatTriggeredAbility(new EzuriClawOfProgressEffect(), TargetController.YOU, false);
        ability.addTarget(new TargetControlledCreaturePermanent(filter2));
        this.addAbility(ability);
    }

    private EzuriClawOfProgress(final EzuriClawOfProgress card) {
        super(card);
    }

    @Override
    public EzuriClawOfProgress copy() {
        return new EzuriClawOfProgress(this);
    }
}

class EzuriClawOfProgressEffect extends OneShotEffect {

    public EzuriClawOfProgressEffect() {
        super(Outcome.Benefit);
        this.staticText = "put X +1/+1 counters on another target creature you control, where X is the number of experience counters you have";
    }

    public EzuriClawOfProgressEffect(final EzuriClawOfProgressEffect effect) {
        super(effect);
    }

    @Override
    public EzuriClawOfProgressEffect copy() {
        return new EzuriClawOfProgressEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Permanent target = game.getPermanent(getTargetPointer().getFirst(game, source));
            if (target != null) {
                int amount = controller.getCounters().getCount(CounterType.EXPERIENCE);
                target.addCounters(CounterType.P1P1.createInstance(amount), source.getControllerId(), source, game);
            }
            return true;
        }
        return false;
    }
}
