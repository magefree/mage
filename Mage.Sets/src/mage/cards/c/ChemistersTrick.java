package mage.cards.c;

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
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;


/**
 * @author LevelX2
 */
public final class ChemistersTrick extends CardImpl {

    public ChemistersTrick(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{U}{R}");

        // Target creature you don't control gets -2/-0 until end of turn and attacks this turn if able.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(StaticFilters.FILTER_CREATURE_YOU_DONT_CONTROL));
        this.getSpellAbility().addEffect(new BoostTargetEffect(-2, 0, Duration.EndOfTurn));
        this.getSpellAbility().addEffect(new AttacksIfAbleTargetEffect(Duration.EndOfTurn));

        // Overload {3}{U}{R} (You may cast this spell for its overload cost. If you do, change its text by replacing all instances of "target" with "each.")
        OverloadAbility ability = new OverloadAbility(this, new BoostAllEffect(-2, 0, Duration.EndOfTurn, StaticFilters.FILTER_CREATURE_YOU_DONT_CONTROL, false), new ManaCostsImpl("{3}{U}{R}"));
        ability.addEffect(new ChemistersTrickEffect());
        this.addAbility(ability);
    }

    private ChemistersTrick(final ChemistersTrick card) {
        super(card);
    }

    @Override
    public ChemistersTrick copy() {
        return new ChemistersTrick(this);
    }
}

class ChemistersTrickEffect extends OneShotEffect {


    ChemistersTrickEffect() {
        super(Outcome.ReturnToHand);
        staticText = "each creature you don't control attacks this turn if able";
    }

    private ChemistersTrickEffect(final ChemistersTrickEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Permanent creature : game.getBattlefield().getActivePermanents(StaticFilters.FILTER_CREATURE_YOU_DONT_CONTROL, source.getControllerId(), source, game)) {
            AttacksIfAbleTargetEffect effect = new AttacksIfAbleTargetEffect(Duration.EndOfTurn);
            effect.setTargetPointer(new FixedTarget(creature.getId(), game));
            game.addEffect(effect, source);
        }
        return true;
    }

    @Override
    public ChemistersTrickEffect copy() {
        return new ChemistersTrickEffect(this);
    }
}
