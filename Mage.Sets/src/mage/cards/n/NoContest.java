
package mage.cards.n;

import java.util.Set;
import java.util.UUID;
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

    public NoContest(final NoContest card) {
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

    public TargetCreatureWithLessPowerPermanent(final TargetCreatureWithLessPowerPermanent target) {
        super(target);
    }

    @Override
    public boolean canChoose(UUID sourceId, UUID sourceControllerId, Game game) {
        int maxPower = Integer.MIN_VALUE; // get the most poerful controlled creature that can be targeted
        Card sourceCard = game.getCard(sourceId);
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURES, sourceControllerId, game)) {
            if (permanent.getPower().getValue() > maxPower && permanent.canBeTargetedBy(sourceCard, sourceControllerId, game)) {
                maxPower = permanent.getPower().getValue();
            }
        }
        // now check, if another creature has less power and can be targeted
        FilterCreaturePermanent checkFilter = new FilterCreaturePermanent();
        checkFilter.add(new PowerPredicate(ComparisonType.FEWER_THAN, maxPower));
        for (Permanent permanent : game.getBattlefield().getActivePermanents(checkFilter, sourceControllerId, sourceId, game)) {
            if (permanent.canBeTargetedBy(sourceCard, sourceControllerId, game)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceId, UUID sourceControllerId, Game game) {
        Spell spell = game.getStack().getSpell(sourceId);
        if (spell != null) {
            Permanent firstTarget = getPermanentFromFirstTarget(spell.getSpellAbility(), game);
            if (firstTarget != null) {
                int power = firstTarget.getPower().getValue();
                // overwrite the filter with the power predicate
                filter = new FilterCreaturePermanent("creature with power less than " + power);
                filter.add(new PowerPredicate(ComparisonType.FEWER_THAN, power));
            }
        }
        return super.possibleTargets(sourceId, sourceControllerId, game);
    }

    private Permanent getPermanentFromFirstTarget(Ability source, Game game) {
        Permanent firstTarget = null;
        if (source.getTargets().size() == 2) {
            firstTarget = game.getPermanent(source.getTargets().get(0).getFirstTarget());
        }
        return firstTarget;
    }

    @Override
    public TargetCreatureWithLessPowerPermanent copy() {
        return new TargetCreatureWithLessPowerPermanent(this);
    }

}
