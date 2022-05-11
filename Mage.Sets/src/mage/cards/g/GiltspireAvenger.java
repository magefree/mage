
package mage.cards.g;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.ExaltedAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.watchers.common.PlayerDamagedBySourceWatcher;

/**
 * @author BetaSteward_at_googlemail.com
 */
public final class GiltspireAvenger extends CardImpl {

    public GiltspireAvenger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}{W}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Exalted (Whenever a creature you control attacks alone, that creature gets +1/+1 until end of turn.)
        this.addAbility(new ExaltedAbility());

        // {T}: Destroy target creature that dealt damage to you this turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DestroyTargetEffect(), new TapSourceCost());
        ability.addTarget(new GiltspireAvengerTarget());
        this.addAbility(ability);

    }

    private GiltspireAvenger(final GiltspireAvenger card) {
        super(card);
    }

    @Override
    public GiltspireAvenger copy() {
        return new GiltspireAvenger(this);
    }
}

class GiltspireAvengerTarget extends TargetPermanent {

    public GiltspireAvengerTarget() {
        super(1, 1, StaticFilters.FILTER_PERMANENT_CREATURE, false);
        targetName = "creature that dealt damage to you this turn";
    }

    public GiltspireAvengerTarget(final GiltspireAvengerTarget target) {
        super(target);
    }

    @Override
    public boolean canTarget(UUID id, Ability source, Game game) {
        PlayerDamagedBySourceWatcher watcher = game.getState().getWatcher(PlayerDamagedBySourceWatcher.class, source.getControllerId());
        if (watcher != null && watcher.hasSourceDoneDamage(id, game)) {
            return super.canTarget(id, source, game);
        }
        return false;
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceControllerId, Ability source, Game game) {
        Set<UUID> availablePossibleTargets = super.possibleTargets(sourceControllerId, source, game);
        Set<UUID> possibleTargets = new HashSet<>();
        PlayerDamagedBySourceWatcher watcher = game.getState().getWatcher(PlayerDamagedBySourceWatcher.class, sourceControllerId);
        for (UUID targetId : availablePossibleTargets) {
            Permanent permanent = game.getPermanent(targetId);
            if (permanent != null && watcher != null && watcher.hasSourceDoneDamage(targetId, game)) {
                possibleTargets.add(targetId);
            }
        }
        return possibleTargets;
    }

    @Override
    public boolean canChoose(UUID sourceControllerId, Ability source, Game game) {
        int remainingTargets = this.minNumberOfTargets - targets.size();
        if (remainingTargets == 0) {
            return true;
        }
        int count = 0;
        MageObject targetSource = game.getObject(source);
        PlayerDamagedBySourceWatcher watcher = game.getState().getWatcher(PlayerDamagedBySourceWatcher.class, sourceControllerId);
        for (Permanent permanent : game.getBattlefield().getActivePermanents(filter, sourceControllerId, source, game)) {
            if (!targets.containsKey(permanent.getId()) && permanent.canBeTargetedBy(targetSource, sourceControllerId, game)
                    && watcher != null && watcher.hasSourceDoneDamage(permanent.getId(), game)) {
                count++;
                if (count >= remainingTargets) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public GiltspireAvengerTarget copy() {
        return new GiltspireAvengerTarget(this);
    }
}
