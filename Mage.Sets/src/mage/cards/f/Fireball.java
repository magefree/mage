package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.costs.CostAdjuster;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetAnyTarget;

import java.util.*;

/**
 * @author BetaSteward_at_googlemail.com
 */
public final class Fireball extends CardImpl {

    public Fireball(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{R}");

        // Fireball deals X damage divided evenly, rounded down, among any number of target creatures and/or players.
        // Fireball costs 1 more to cast for each target beyond the first.
        this.getSpellAbility().addTarget(new FireballTargetCreatureOrPlayer(0, Integer.MAX_VALUE));
        this.getSpellAbility().addEffect(new FireballEffect());
        this.getSpellAbility().setCostAdjuster(FireballAdjuster.instance);
    }

    private Fireball(final Fireball card) {
        super(card);
    }

    @Override
    public Fireball copy() {
        return new Fireball(this);
    }
}

enum FireballAdjuster implements CostAdjuster {
    instance;

    @Override
    public void adjustCosts(Ability ability, Game game) {
        int numTargets = ability.getTargets().isEmpty() ? 0 : ability.getTargets().get(0).getTargets().size();
        if (numTargets > 1) {
            ability.getManaCostsToPay().add(new GenericManaCost(numTargets - 1));
        }
    }
}

class FireballEffect extends OneShotEffect {

    public FireballEffect() {
        super(Outcome.Damage);
        staticText = "this spell costs {1} more to cast for each target beyond the first.<br> {this} deals " +
                "X damage divided evenly, rounded down, among any number of targets";
    }

    public FireballEffect(final FireballEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int numTargets = targetPointer.getTargets(game, source).size();
        int damage = source.getManaCostsToPay().getX();
        if (numTargets > 0) {
            int damagePer = damage / numTargets;
            if (damagePer > 0) {
                for (UUID targetId : targetPointer.getTargets(game, source)) {
                    Permanent permanent = game.getPermanent(targetId);
                    if (permanent != null) {
                        permanent.damage(damagePer, source.getSourceId(), source, game, false, true);
                    } else {
                        Player player = game.getPlayer(targetId);
                        if (player != null) {
                            player.damage(damagePer, source.getSourceId(), source, game);
                        }
                    }
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public FireballEffect copy() {
        return new FireballEffect(this);
    }

}

class FireballTargetCreatureOrPlayer extends TargetAnyTarget {

    public FireballTargetCreatureOrPlayer(int minNumTargets, int maxNumTargets) {
        super(minNumTargets, maxNumTargets);
    }

    public FireballTargetCreatureOrPlayer(final FireballTargetCreatureOrPlayer target) {
        super(target);
    }

    /**
     * This is only used by AI players
     *
     * @param source
     * @param game
     * @return
     */
    @Override
    public List<TargetAnyTarget> getTargetOptions(Ability source, Game game) {

        List<TargetAnyTarget> options = new ArrayList<>();
        int xVal = source.getManaCostsToPay().getX();

        if (xVal < 1) {
            return options;
        }

        for (int numberTargets = 1; numberTargets == 1 || xVal / (numberTargets - 1) > 1; numberTargets++) {
            Set<UUID> possibleTargets = possibleTargets(source.getControllerId(), source, game);
            // less possible targets than we're trying to set
            if (possibleTargets.size() < numberTargets) {
                return options;
            }
            // less than 1 damage per target = 0, add no such options
            if ((xVal - (numberTargets - 1)) / numberTargets < 1) {
                continue;
            }

            possibleTargets.removeAll(getTargets());
            for (UUID targetId : possibleTargets) {
                TargetAnyTarget target = this.copy();
                target.clearChosen();
                target.addTarget(targetId, source, game, true);

                if (target.getTargets().size() == numberTargets) {
                    chosen = true;
                }

                if (!target.isChosen()) {
                    Iterator<UUID> it2 = possibleTargets.iterator();
                    while (it2.hasNext() && !target.isChosen()) {
                        UUID nextTargetId = it2.next();
                        target.addTarget(nextTargetId, source, game, true);

                        if (target.getTargets().size() == numberTargets) {
                            chosen = true;
                        }

                    }
                }
                if (target.isChosen()) {
                    options.add(target);
                }
            }
        }
        return options;
    }

    @Override
    public FireballTargetCreatureOrPlayer copy() {
        return new FireballTargetCreatureOrPlayer(this);
    }
}
