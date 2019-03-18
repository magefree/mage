
package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.combat.AttacksIfAbleTargetEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.OverloadAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;


/**
 *
 * @author LevelX2
 */
public final class ChemistersTrick extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature you don't control");

    static {
        filter.add(new ControllerPredicate(TargetController.NOT_YOU));
    }

    public ChemistersTrick(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{U}{R}");


        // Target creature you don't control gets -2/-0 until end of turn and attacks this turn if able.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(filter));
        this.getSpellAbility().addEffect(new BoostTargetEffect(-2,0, Duration.EndOfTurn));
        this.getSpellAbility().addEffect(new AttacksIfAbleTargetEffect(Duration.EndOfTurn));

        // Overload {3}{U}{R} (You may cast this spell for its overload cost. If you do, change its text by replacing all instances of "target" with "each.")
        OverloadAbility ability = new OverloadAbility(this, new BoostAllEffect(-2,0, Duration.EndOfTurn,filter,false), new ManaCostsImpl("{3}{U}{R}"));
        ability.addEffect(new ChemistersTrickEffect(filter));
        this.addAbility(ability);
    }

    public ChemistersTrick(final ChemistersTrick card) {
        super(card);
    }

    @Override
    public ChemistersTrick copy() {
        return new ChemistersTrick(this);
    }
}

class ChemistersTrickEffect extends OneShotEffect {

    private FilterCreaturePermanent filter;

    public ChemistersTrickEffect(FilterCreaturePermanent filter) {
        super(Outcome.ReturnToHand);
        staticText = "each creature you don't control attacks this turn if able";
        this.filter = filter;
    }

    public ChemistersTrickEffect(final ChemistersTrickEffect effect) {
        super(effect);
        this.filter = effect.filter;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Permanent creature : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source.getSourceId(), game)) {
            AttacksIfAbleTargetEffect effect = new AttacksIfAbleTargetEffect(Duration.EndOfTurn);
            effect.setTargetPointer(new FixedTarget(creature.getId()));
            game.addEffect(effect, source);
        }
        return true;
    }

    @Override
    public ChemistersTrickEffect copy() {
        return new ChemistersTrickEffect(this);
    }

}
