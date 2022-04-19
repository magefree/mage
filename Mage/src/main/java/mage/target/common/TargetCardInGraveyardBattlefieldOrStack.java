package mage.target.common;

import mage.MageObject;
import mage.abilities.Ability;
import mage.constants.ComparisonType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.target.TargetCard;

import java.util.Set;
import java.util.UUID;

/**
 * @author LevelX2
 */
public class TargetCardInGraveyardBattlefieldOrStack extends TargetCard {

    private static final FilterSpell defaultSpellFilter = new FilterSpell();

    static {
        defaultSpellFilter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, -1));
    }

    protected final FilterPermanent filterPermanent;
    protected final FilterSpell filterSpell;

    public TargetCardInGraveyardBattlefieldOrStack(int minNumTargets, int maxNumTargets, FilterCard filterGraveyard, FilterPermanent filterBattlefield) {
        this(minNumTargets, maxNumTargets, filterGraveyard, filterBattlefield, defaultSpellFilter, null);
    }

    public TargetCardInGraveyardBattlefieldOrStack(int minNumTargets, int maxNumTargets, FilterCard filterGraveyard, FilterPermanent filterBattlefield, FilterSpell filterSpell, String targetName) {
        super(minNumTargets, maxNumTargets, Zone.GRAVEYARD, filterGraveyard); // zone for card in graveyard, don't change
        this.filterPermanent = filterBattlefield;
        this.filterSpell = filterSpell;
        this.targetName = targetName != null ? targetName : filter.getMessage()
                + " in a graveyard "
                + (maxNumTargets > 1 ? " and/or " : " or ")
                + this.filterPermanent.getMessage()
                + " on the battlefield";
    }

    public TargetCardInGraveyardBattlefieldOrStack(final TargetCardInGraveyardBattlefieldOrStack target) {
        super(target);
        this.filterPermanent = target.filterPermanent;
        this.filterSpell = target.filterSpell;
    }

    @Override
    public boolean canChoose(UUID sourceControllerId, Ability source, Game game) {
        if (super.canChoose(sourceControllerId, source, game)) {
            return true;
        }
        MageObject targetSource = game.getObject(source);
        for (Permanent permanent : game.getBattlefield().getActivePermanents(filterPermanent, sourceControllerId, source, game)) {
            if (notTarget || permanent.canBeTargetedBy(targetSource, sourceControllerId, game)) {
                return true;
            }
        }
        for (StackObject stackObject : game.getStack()) {
            if (stackObject instanceof Spell
                    && game.getState().getPlayersInRange(sourceControllerId, game).contains(stackObject.getControllerId())
                    && filterSpell.match(stackObject, sourceControllerId, source, game)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean canTarget(UUID id, Ability source, Game game) {
        return this.canTarget(source.getControllerId(), id, source, game);
    }

    @Override
    public boolean canTarget(UUID playerId, UUID id, Ability source, Game game) {
        if (super.canTarget(playerId, id, source, game)) { // in graveyard first
            return true;
        }
        Permanent permanent = game.getPermanent(id);
        if (permanent != null) {
            return filterPermanent.match(permanent, playerId, source, game);
        }
        Spell spell = game.getSpell(id);
        return spell != null && filterSpell.match(spell, playerId, source, game);
    }

    @Override
    public boolean canTarget(UUID id, Game game) {
        return this.canTarget(null, id, null, game);
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceControllerId, Game game) {
        return this.possibleTargets(sourceControllerId, (Ability) null, game);
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceControllerId, Ability source, Game game) {
        Set<UUID> possibleTargets = super.possibleTargets(sourceControllerId, source, game); // in graveyard first
        MageObject targetSource = game.getObject(source);
        for (Permanent permanent : game.getBattlefield().getActivePermanents(filterPermanent, sourceControllerId, source, game)) {
            if (notTarget || permanent.canBeTargetedBy(targetSource, sourceControllerId, game)) {
                possibleTargets.add(permanent.getId());
            }
        }
        for (StackObject stackObject : game.getStack()) {
            if (stackObject instanceof Spell
                    && game.getState().getPlayersInRange(sourceControllerId, game).contains(stackObject.getControllerId())
                    && filterSpell.match(stackObject, sourceControllerId, source, game)) {
                possibleTargets.add(stackObject.getId());
            }
        }
        return possibleTargets;
    }

    @Override
    public TargetCardInGraveyardBattlefieldOrStack copy() {
        return new TargetCardInGraveyardBattlefieldOrStack(this);
    }
}
