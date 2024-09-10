package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.ModifiedPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.SecondTargetPointer;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class SignatureSlam extends CardImpl {

    public SignatureSlam(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{G}");

        // Put a +1/+1 counter on target creature you control, then each modified creature you control deals damage equal to its power to target creature you don't control.
        this.getSpellAbility().addEffect(new AddCountersTargetEffect(CounterType.P1P1.createInstance()));
        this.getSpellAbility().addEffect(new SignatureSlamEffect().setTargetPointer(new SecondTargetPointer()));
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(StaticFilters.FILTER_CREATURE_YOU_DONT_CONTROL));
    }

    private SignatureSlam(final SignatureSlam card) {
        super(card);
    }

    @Override
    public SignatureSlam copy() {
        return new SignatureSlam(this);
    }
}

class SignatureSlamEffect extends OneShotEffect {

    private static final FilterControlledCreaturePermanent filter =
            new FilterControlledCreaturePermanent("modified creature you control");

    static {
        filter.add(ModifiedPredicate.instance);
    }

    SignatureSlamEffect() {
        super(Outcome.Damage);
        this.staticText = ", then each modified creature you control deals damage equal to its power to target creature you don't control";
    }

    private SignatureSlamEffect(final SignatureSlamEffect effect) {
        super(effect);
    }

    @Override
    public SignatureSlamEffect copy() {
        return new SignatureSlamEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent targetCreature = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (controller == null || targetCreature == null) {
            return false;
        }
        boolean result = false;
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(filter, controller.getId(), game)) {
            result |= 0 < targetCreature.damage(permanent.getPower().getValue(), permanent.getId(), source, game);
        }
        return result;
    }
}
