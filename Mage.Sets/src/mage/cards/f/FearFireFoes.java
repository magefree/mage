package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.DamageCantBePreventedEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.MageObjectReferencePredicate;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FearFireFoes extends CardImpl {

    public FearFireFoes(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{R}");

        // Damage can't be prevented this turn. Fear, Fire, Foes! deals X damage to target creature and 1 damage to each other creature with the same controller.
        this.getSpellAbility().addEffect(new DamageCantBePreventedEffect(
                Duration.EndOfTurn, "Damage can't be prevented this turn"
        ));
        this.getSpellAbility().addEffect(new DamageTargetEffect(ManacostVariableValue.REGULAR));
        this.getSpellAbility().addEffect(new FearFireFoesEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private FearFireFoes(final FearFireFoes card) {
        super(card);
    }

    @Override
    public FearFireFoes copy() {
        return new FearFireFoes(this);
    }
}

class FearFireFoesEffect extends OneShotEffect {

    FearFireFoesEffect() {
        super(Outcome.Benefit);
        staticText = "and 1 damage to each other creature with the same controller";
    }

    private FearFireFoesEffect(final FearFireFoesEffect effect) {
        super(effect);
    }

    @Override
    public FearFireFoesEffect copy() {
        return new FearFireFoesEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent == null) {
            return false;
        }
        FilterPermanent filter = new FilterCreaturePermanent();
        filter.add(Predicates.not(new MageObjectReferencePredicate(permanent, game)));
        filter.add(new ControllerIdPredicate(permanent.getControllerId()));
        return new DamageAllEffect(1, filter).apply(game, source);
    }
}
