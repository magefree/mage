package mage.cards.i;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author jeffwadsworth
 */
public final class Insurrection extends CardImpl {

    public Insurrection(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{5}{R}{R}{R}");

        // Untap all creatures and gain control of them until end of turn. They gain haste until end of turn.
        this.getSpellAbility().addEffect(new InsurrectionEffect());
    }

    private Insurrection(final Insurrection card) {
        super(card);
    }

    @Override
    public Insurrection copy() {
        return new Insurrection(this);
    }
}

class InsurrectionEffect extends OneShotEffect {

    public InsurrectionEffect() {
        super(Outcome.Benefit);
        this.staticText = "Untap all creatures and gain control of them until end of turn. They gain haste until end of turn";
    }

    public InsurrectionEffect(final InsurrectionEffect effect) {
        super(effect);
    }

    @Override
    public InsurrectionEffect copy() {
        return new InsurrectionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        boolean result = false;
        ContinuousEffect gainControl = new GainControlTargetEffect(Duration.EndOfTurn);
        ContinuousEffect gainHaste = new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.EndOfTurn);
        for (Permanent creature : game.getBattlefield().getAllActivePermanents(CardType.CREATURE, game)) {
            creature.untap(game);
            gainControl.setTargetPointer(new FixedTarget(creature.getId(), game));
            gainHaste.setTargetPointer(new FixedTarget(creature.getId(), game));
            game.addEffect(gainControl, source);
            game.addEffect(gainHaste, source);
            result = true;
        }
        return result;
    }
}
