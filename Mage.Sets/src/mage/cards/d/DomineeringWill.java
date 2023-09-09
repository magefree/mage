
package mage.cards.d;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.RequirementEffect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.combat.BlocksIfAbleTargetEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.AttackingPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.SecondTargetPointer;

/**
 *
 * @author LevelX2
 */
public final class DomineeringWill extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("nonattacking creatures");

    static {
        filter.add(Predicates.not(AttackingPredicate.instance));
    }

    public DomineeringWill(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{3}{U}");

        // Target player gains control of up to three target nonattacking creatures until end of turn. Untap those creatures. They block this turn if able.
        this.getSpellAbility().addEffect(new DomineeringWillEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(0, 3, filter, false));

    }

    private DomineeringWill(final DomineeringWill card) {
        super(card);
    }

    @Override
    public DomineeringWill copy() {
        return new DomineeringWill(this);
    }
}

class DomineeringWillEffect extends OneShotEffect {

    public DomineeringWillEffect() {
        super(Outcome.Benefit);
        staticText = "Target player gains control of up to three target nonattacking creatures until end of turn. Untap those creatures. They block this turn if able";
    }

    private DomineeringWillEffect(final DomineeringWillEffect effect) {
        super(effect);
    }

    @Override
    public DomineeringWillEffect copy() {
        return new DomineeringWillEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (targetPlayer != null) {
            ContinuousEffect effect = new GainControlTargetEffect(Duration.EndOfTurn, targetPlayer.getId());
            effect.setTargetPointer(new SecondTargetPointer());
            effect.setText("Target player gains control of up to three target nonattacking creatures until end of turn");
            game.addEffect(effect, source);

            Effect effect2 = new UntapTargetEffect();
            effect2.setTargetPointer(new SecondTargetPointer());
            effect2.setText("Untap those creatures");
            effect2.apply(game, source);

            RequirementEffect effect3 = new BlocksIfAbleTargetEffect(Duration.EndOfTurn);
            effect3.setTargetPointer(new SecondTargetPointer());
            effect3.setText("They block this turn if able");
            game.addEffect(effect3, source);
            return true;
        }
        return false;
    }
}
