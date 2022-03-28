
package mage.abilities.keyword;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.constants.SetTargetPointer;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicate;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledPermanent;
import mage.util.GameLog;

/**
 * 702.94. Soulbond
 *
 * 702.94a Soulbond is a keyword that represents two triggered abilities.
 * “Soulbond” means “When this creature enters the battlefield, if you control
 * both this creature and another creature and both are unpaired, you may pair
 * this creature with another unpaired creature you control for as long as both
 * remain creatures on the battlefield under your control” and “Whenever another
 * creature enters the battlefield under your control, if you control both that
 * creature and this one and both are unpaired, you may pair that creature with
 * this creature for as long as both remain creatures on the battlefield under
 * your control.”
 *
 * 702.94b A creature becomes “paired” with another as the result of a soulbond
 * ability. Abilities may refer to a paired creature, the creature another
 * creature is paired with, or whether a creature is paired. An “unpaired”
 * creature is one that is not paired.
 *
 * 702.94c When the soulbond ability resolves, if either object that would be
 * paired is no longer a creature, no longer on the battlefield, or no longer
 * under the control of the player who controls the soulbond ability, neither
 * object becomes paired.
 *
 * 702.94d A creature can be paired with only one other creature.
 *
 * 702.94e A paired creature becomes unpaired if any of the following occur:
 * another player gains control of it or the creature it's paired with; it or
 * the creature it's paired with stops being a creature; or it or the creature
 * it's paired with leaves the battlefield.
 *
 * @author LevelX2
 */
public class SoulbondAbility extends EntersBattlefieldTriggeredAbility {

    public SoulbondAbility() {
        super(new SoulboundEntersSelfEffect(), true);
        this.addSubAbility(new SoulbondEntersOtherAbility());
    }

    public SoulbondAbility(SoulbondAbility ability) {
        super(ability);
    }

    @Override
    public String getRule() {
        return "Soulbond <i>(You may pair this creature with another unpaired creature when either enters the battlefield. They remain paired for as long as you control both of them.)</i>";
    }

    @Override
    public boolean checkInterveningIfClause(Game game) {
        // if you control both this creature and another creature and both are unpaired
        boolean self = false;
        boolean other = false;
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(getControllerId())) {
            if (permanent.isCreature(game)) {
                if (permanent.getId().equals(getSourceId())) {
                    if (permanent.isControlledBy(getControllerId())) {
                        self = true;
                        if (other) {
                            return true;
                        }
                    } else {
                        return false;
                    }
                } else if (permanent.getPairedCard() == null) {
                    other = true;
                    if (self) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public SoulbondAbility copy() {
        return new SoulbondAbility(this);

    }

}
// When this creature enters the battlefield, if you control both this creature and another creature and both are unpaired, you may pair
// this creature with another unpaired creature you control for as long as both remain creatures on the battlefield under your control

class SoulboundEntersSelfEffect extends OneShotEffect {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("another not paired creature you control");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(Predicates.not(new PairedPredicate()));
    }

    public SoulboundEntersSelfEffect() {
        super(Outcome.Benefit);
    }

    public SoulboundEntersSelfEffect(final SoulboundEntersSelfEffect effect) {
        super(effect);
    }

    @Override
    public SoulboundEntersSelfEffect copy() {
        return new SoulboundEntersSelfEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null && permanent.isCreature(game)) {
            Player controller = game.getPlayer(permanent.getControllerId());
            if (controller != null) {
                TargetControlledPermanent target = new TargetControlledPermanent(filter);
                target.setNotTarget(true);
                if (target.canChoose(controller.getId(), source, game)) {
                    if (controller.choose(Outcome.Benefit, target, source, game)) {
                        Permanent chosen = game.getPermanent(target.getFirstTarget());
                        if (chosen != null) {
                            chosen.setPairedCard(new MageObjectReference(permanent, game));
                            chosen.addInfo("soulbond", "Soulbond to " + GameLog.getColoredObjectIdNameForTooltip(permanent), game);
                            permanent.setPairedCard(new MageObjectReference(chosen, game));
                            permanent.addInfo("soulbond", "Soulbond to " + GameLog.getColoredObjectIdNameForTooltip(chosen), game);
                            if (!game.isSimulation()) {
                                game.informPlayers(controller.getLogName() + " soulbonds " + permanent.getLogName() + " with " + chosen.getLogName());
                            }
                        }
                    }
                }

            }
            return true;
        }
        return false;
    }
}

/**
 * “Whenever another creature enters the battlefield under your control, if you
 * control both that creature and this one and both are unpaired, you may pair
 * that creature with this creature for as long as both remain creatures on the
 * battlefield under your control.”
 *
 */
class SoulbondEntersOtherAbility extends EntersBattlefieldAllTriggeredAbility {

    private static final FilterCreaturePermanent soulbondFilter = new FilterCreaturePermanent();

    static {
        soulbondFilter.add(Predicates.not(new PairedPredicate()));
        soulbondFilter.add(TargetController.YOU.getControllerPredicate());
        soulbondFilter.add(AnotherPredicate.instance);
    }

    public SoulbondEntersOtherAbility() {
        super(Zone.BATTLEFIELD, new SoulboundEntersOtherEffect(), soulbondFilter, true, SetTargetPointer.PERMANENT, "");
        setRuleVisible(false);
    }

    public SoulbondEntersOtherAbility(SoulbondEntersOtherAbility ability) {
        super(ability);
    }

    @Override
    public String getRule() {
        return "Soulbond <i>(You may pair this creature with another unpaired creature when either enters the battlefield. They remain paired for as long as you control both of them.)</i>";
    }

    @Override
    public boolean checkInterveningIfClause(Game game) {
        // if you control both this creature and another creature and both are unpaired
        if (game.getBattlefield().countAll(filter, getControllerId(), game) > 0) {
            Permanent sourcePermanent = game.getPermanent(getSourceId());
            if (sourcePermanent != null && sourcePermanent.isControlledBy(getControllerId()) && sourcePermanent.getPairedCard() == null) {
                return true;
            }
        }
        return false;
    }

    @Override
    public SoulbondEntersOtherAbility copy() {
        return new SoulbondEntersOtherAbility(this);
    }

}

class SoulboundEntersOtherEffect extends OneShotEffect {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("another not paired creature you control");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(Predicates.not(new PairedPredicate()));
    }

    public SoulboundEntersOtherEffect() {
        super(Outcome.Benefit);
    }

    public SoulboundEntersOtherEffect(final SoulboundEntersOtherEffect effect) {
        super(effect);
    }

    @Override
    public SoulboundEntersOtherEffect copy() {
        return new SoulboundEntersOtherEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null && permanent.getPairedCard() == null
                && permanent.isCreature(game)) {
            Player controller = game.getPlayer(permanent.getControllerId());
            if (controller != null) {
                Permanent enteringPermanent = game.getPermanent(getTargetPointer().getFirst(game, source));
                if (enteringPermanent != null && enteringPermanent.isCreature(game) && enteringPermanent.getPairedCard() == null) {
                    enteringPermanent.setPairedCard(new MageObjectReference(permanent, game));
                    permanent.setPairedCard(new MageObjectReference(enteringPermanent, game));
                    if (!game.isSimulation()) {
                        game.informPlayers(controller.getLogName() + " soulbonds " + permanent.getLogName() + " with " + enteringPermanent.getLogName());
                    }
                }
            }

            return true;
        }

        return false;
    }

}

class PairedPredicate implements Predicate<Permanent> {

    @Override
    public boolean apply(Permanent input, Game game) {
        return input.getPairedCard() != null;
    }

    @Override
    public String toString() {
        return "Paired";
    }

}
