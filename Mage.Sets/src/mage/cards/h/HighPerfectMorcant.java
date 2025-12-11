package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.EntersBattlefieldThisOrAnotherTriggeredAbility;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.ProliferateEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HighPerfectMorcant extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.ELF, "Elf");
    private static final FilterControlledPermanent filter2
            = new FilterControlledPermanent(SubType.ELF, "untapped Elves you control");

    static {
        filter2.add(TappedPredicate.UNTAPPED);
    }

    public HighPerfectMorcant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Whenever High Perfect Morcant or another Elf you control enters, each opponent blights 1.
        this.addAbility(new EntersBattlefieldThisOrAnotherTriggeredAbility(
                new HighPerfectMorcantEffect(), filter, false, true
        ));

        // Tap three untapped Elves you control: Proliferate. Activate only as a sorcery.
        this.addAbility(new ActivateAsSorceryActivatedAbility(
                new ProliferateEffect(), new TapTargetCost(3, filter2)
        ));
    }

    private HighPerfectMorcant(final HighPerfectMorcant card) {
        super(card);
    }

    @Override
    public HighPerfectMorcant copy() {
        return new HighPerfectMorcant(this);
    }
}

class HighPerfectMorcantEffect extends OneShotEffect {

    HighPerfectMorcantEffect() {
        super(Outcome.Benefit);
        staticText = "each opponent blights 1. <i>(They each put a -1/-1 counter on a creature they control.)</i>";
    }

    private HighPerfectMorcantEffect(final HighPerfectMorcantEffect effect) {
        super(effect);
    }

    @Override
    public HighPerfectMorcantEffect copy() {
        return new HighPerfectMorcantEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        // TODO: this will likely be refactored when we learn more about this mechanic
        for (UUID opponentId : game.getOpponents(source.getControllerId())) {
            Player opponent = game.getPlayer(opponentId);
            if (opponent == null || !game.getBattlefield().contains(
                    StaticFilters.FILTER_CONTROLLED_CREATURE, opponentId, source, game, 1
            )) {
                continue;
            }
            TargetPermanent target = new TargetControlledCreaturePermanent();
            target.withNotTarget(true);
            target.withChooseHint("to put a -1/-1 counter on");
            opponent.choose(outcome, target, source, game);
            Optional.ofNullable(target)
                    .map(TargetPermanent::getFirstTarget)
                    .map(game::getPermanent)
                    .ifPresent(permanent -> permanent.addCounters(
                            CounterType.M1M1.createInstance(), opponentId, source, game
                    ));
        }
        return true;
    }
}
