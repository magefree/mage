package mage.cards.h;

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardSetInfo;
import mage.cards.SplitCard;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SpellAbilityType;
import mage.filter.common.FilterAttackingCreature;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetControlledPermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class HitRun extends SplitCard {

    public HitRun(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{B}{R}", "{3}{R}{G}", SpellAbilityType.SPLIT);

        // Hit
        // Target player sacrifices an artifact or creature. Hit deals damage to that player equal to that permanent's converted mana cost.
        getLeftHalfCard().getSpellAbility().addEffect(new HitEffect());
        getLeftHalfCard().getSpellAbility().addTarget(new TargetPlayer());

        // Run
        // Attacking creatures you control get +1/+0 until end of turn for each other attacking creature.
        getRightHalfCard().getSpellAbility().addEffect(new RunEffect());

    }

    private HitRun(final HitRun card) {
        super(card);
    }

    @Override
    public HitRun copy() {
        return new HitRun(this);
    }
}

class HitEffect extends OneShotEffect {

    public HitEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "Target player sacrifices an artifact or creature. Hit deals damage to that player equal to that permanent's mana value";
    }

    public HitEffect(final HitEffect effect) {
        super(effect);
    }

    @Override
    public HitEffect copy() {
        return new HitEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(source.getTargets().getFirstTarget());
        if (targetPlayer != null) {
            FilterControlledPermanent filter = new FilterControlledPermanent("artifact or creature");
            filter.add(Predicates.or(
                    CardType.ARTIFACT.getPredicate(),
                    CardType.CREATURE.getPredicate()));
            TargetControlledPermanent target = new TargetControlledPermanent(1, 1, filter, true);

            if (target.canChoose(targetPlayer.getId(), source, game)) {
                targetPlayer.choose(Outcome.Sacrifice, target, source, game);
                Permanent permanent = game.getPermanent(target.getFirstTarget());
                if (permanent != null) {
                    permanent.sacrifice(source, game);
                    int damage = permanent.getManaValue();
                    if (damage > 0) {
                        targetPlayer.damage(damage, source.getSourceId(), source, game);
                    }
                }
            }
        }
        return true;
    }
}

class RunEffect extends OneShotEffect {

    public RunEffect() {
        super(Outcome.BoostCreature);
        this.staticText = "Attacking creatures you control get +1/+0 until end of turn for each other attacking creature";
    }

    public RunEffect(final RunEffect effect) {
        super(effect);
    }

    @Override
    public RunEffect copy() {
        return new RunEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int attackingCreatures = game.getBattlefield().count(new FilterAttackingCreature(), controller.getId(), source, game);
            if (attackingCreatures > 1) {
                for (Permanent permanent : game.getBattlefield().getAllActivePermanents(new FilterAttackingCreature(), controller.getId(), game)) {
                    ContinuousEffect effect = new BoostTargetEffect(attackingCreatures - 1, 0, Duration.EndOfTurn);
                    effect.setTargetPointer(new FixedTarget(permanent, game));
                    game.addEffect(effect, source);
                }
            }
            return true;
        }
        return false;
    }
}
