package mage.cards.n;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.effects.common.FightTargetsEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.watchers.common.BlockedAttackerWatcher;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author Styxo
 */
public final class NoContest extends CardImpl {

    public NoContest(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{G}");

        // Target creature you control fights target creature with power less than its power.
        this.getSpellAbility().addEffect(new FightTargetsEffect());
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        this.getSpellAbility().addTarget(new TargetCreatureWithLessPowerPermanent());

    }

    private NoContest(final NoContest card) {
        super(card);
    }

    @Override
    public NoContest copy() {
        return new NoContest(this);
    }
}

class TargetCreatureWithLessPowerPermanent extends TargetPermanent {

    public TargetCreatureWithLessPowerPermanent() {
        super(1, 1, new FilterCreaturePermanent("creature with power less than its power"), false);
    }

    private TargetCreatureWithLessPowerPermanent(final TargetCreatureWithLessPowerPermanent target) {
        super(target);
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceControllerId, Ability source, Game game) {
        Set<UUID> possibleTargets = new HashSet<>();

        Permanent firstPermanent = game.getPermanent(source.getFirstTarget());
        for (Permanent permanent : game.getBattlefield().getActivePermanents(filter, sourceControllerId, source, game)) {
            if (firstPermanent == null) {
                // playable or first target not yet selected
                // use all
                possibleTargets.add(permanent.getId());
            } else {
                // real
                // filter by power
                if (firstPermanent.getPower().getValue() > permanent.getPower().getValue()) {
                    possibleTargets.add(permanent.getId());
                }
            }
        }
        possibleTargets.removeIf(id -> firstPermanent != null && firstPermanent.getId().equals(id));

        return keepValidPossibleTargets(possibleTargets, sourceControllerId, source, game);
    }

    @Override
    public TargetCreatureWithLessPowerPermanent copy() {
        return new TargetCreatureWithLessPowerPermanent(this);
    }

}
