package mage.cards.c;

import java.util.UUID;

import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageWithPowerFromOneToAnotherTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreatureOrPlaneswalkerPermanent;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author muz
 */
public final class CompelBrutality extends CardImpl {

    private static final FilterPermanent filter = new FilterCreatureOrPlaneswalkerPermanent("creature or planeswalker an opponent controls");
    private static final FilterControlledPermanent controlledPlaneswalker = new FilterControlledPermanent("planeswalker you control");

    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
        controlledPlaneswalker.add(CardType.PLANESWALKER.getPredicate());
    }

    public CompelBrutality(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{G}");

        // Choose one --
        // * Target creature you control deals damage equal to its power to target creature or planeswalker an opponent controls.
        this.getSpellAbility().addEffect(new DamageWithPowerFromOneToAnotherTargetEffect());
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        this.getSpellAbility().addTarget(new TargetPermanent(filter));

        // * Target planeswalker you control deals damage equal to its loyalty to target creature or planeswalker an opponent controls.
        Mode mode = new Mode(new CompelBrutalityEffect());
        mode.addTarget(new TargetPermanent(controlledPlaneswalker));
        mode.addTarget(new TargetPermanent(filter));
        this.getSpellAbility().addMode(mode);
    }

    private CompelBrutality(final CompelBrutality card) {
        super(card);
    }

    @Override
    public CompelBrutality copy() {
        return new CompelBrutality(this);
    }
}

class CompelBrutalityEffect extends OneShotEffect {

    public CompelBrutalityEffect() {
        super(Outcome.Damage);
        this.staticText = "Target planeswalker you control deals damage equal to its loyalty to target creature or planeswalker an opponent controls";
    }

    private CompelBrutalityEffect(final CompelBrutalityEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(mage.game.Game game, mage.abilities.Ability source) {
        Permanent myPermanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (myPermanent == null) {
            return false;
        }
        int counters = myPermanent.getCounters(game).getCount(CounterType.LOYALTY);
        Permanent anotherPermanent = game.getPermanent(source.getTargets().get(1).getFirstTarget());

        if (anotherPermanent != null && counters > 0) {
            anotherPermanent.damage(counters, myPermanent.getId(), source, game, false, true);
            return true;
        }

        return false;
    }

    @Override
    public CompelBrutalityEffect copy() {
          return new CompelBrutalityEffect(this);
    }
}
