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
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicate;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledPermanent;
import mage.util.GameLog;

/**
 * 702.94. Soulbond
 * <p>
 * 702.94a Soulbond is a keyword that represents two triggered abilities.
 * “Soulbond” means “When this creature enters the battlefield, if you control
 * both this creature and another creature and both are unpaired, you may pair
 * this creature with another unpaired creature you control for as long as both
 * remain creatures on the battlefield under your control” and “Whenever another
 * creature you control enters, if you control both that
 * creature and this one and both are unpaired, you may pair that creature with
 * this creature for as long as both remain creatures on the battlefield under
 * your control.”
 * <p>
 * 702.94b A creature becomes “paired” with another as the result of a soulbond
 * ability. Abilities may refer to a paired creature, the creature another
 * creature is paired with, or whether a creature is paired. An “unpaired”
 * creature is one that is not paired.
 * <p>
 * 702.94c When the soulbond ability resolves, if either object that would be
 * paired is no longer a creature, no longer on the battlefield, or no longer
 * under the control of the player who controls the soulbond ability, neither
 * object becomes paired.
 * <p>
 * 702.94d A creature can be paired with only one other creature.
 * <p>
 * 702.94e A paired creature becomes unpaired if any of the following occur:
 * another player gains control of it or the creature it's paired with; it or
 * the creature it's paired with stops being a creature; or it or the creature
 * it's paired with leaves the battlefield.
 *
 * @author LevelX2
 */
public class SoulbondAbility extends EntersBattlefieldTriggeredAbility {

    public SoulbondAbility() {
        super(new SoulbondEntersSelfEffect(), true);
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
        Permanent sourcePerm = game.getPermanent(getSourceId());
        if (sourcePerm == null
                || !sourcePerm.isControlledBy(getControllerId())
                || !sourcePerm.isCreature(game)
                || sourcePerm.getPairedCard() != null) {
            return false;
        }
        return game.getBattlefield().getAllActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, getControllerId(), game)
                .stream()
                .filter(p -> !p.getId().equals(sourcePerm.getId()))
                .anyMatch(p -> p.getPairedCard() == null);
    }

    @Override
    public SoulbondAbility copy() {
        return new SoulbondAbility(this);

    }

}
/*
 * When this creature enters, if you control both this creature and another creature and both are unpaired,
 * you may pair this creature with another unpaired creature you control
 * for as long as both remain creatures on the battlefield under your control.
 */

class SoulbondEntersSelfEffect extends OneShotEffect {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("another unpaired creature you control");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(PairedPredicate.UNPAIRED);
    }

    SoulbondEntersSelfEffect() {
        super(Outcome.Benefit);
    }

    private SoulbondEntersSelfEffect(final SoulbondEntersSelfEffect effect) {
        super(effect);
    }

    @Override
    public SoulbondEntersSelfEffect copy() {
        return new SoulbondEntersSelfEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourcePerm = source.getSourcePermanentIfItStillExists(game);
        if (sourcePerm == null
                || !sourcePerm.isControlledBy(source.getControllerId())
                || !sourcePerm.isCreature(game)
                || sourcePerm.getPairedCard() != null) {
            return false;
        }
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        TargetControlledPermanent target = new TargetControlledPermanent(filter);
        target.withNotTarget(true);
        if (target.canChoose(controller.getId(), source, game)) {
            if (controller.choose(Outcome.Benefit, target, source, game)) {
                Permanent chosen = game.getPermanent(target.getFirstTarget());
                if (chosen != null) {
                    chosen.setPairedCard(new MageObjectReference(sourcePerm, game));
                    chosen.addInfo("soulbond", "Soulbond to " + GameLog.getColoredObjectIdNameForTooltip(sourcePerm), game);
                    sourcePerm.setPairedCard(new MageObjectReference(chosen, game));
                    sourcePerm.addInfo("soulbond", "Soulbond to " + GameLog.getColoredObjectIdNameForTooltip(chosen), game);
                    game.informPlayers(controller.getLogName() + " soulbonds " + sourcePerm.getLogName() + " with " + chosen.getLogName());
                    return true;
                }
            }
        }
        return false;
    }
}

/**
 * Whenever another creature you control enters, 
 * if you control both that creature and this one and both are unpaired,
 * you may pair that creature with this creature 
 * for as long as both remain creatures on the battlefield under your control.
 */
class SoulbondEntersOtherAbility extends EntersBattlefieldAllTriggeredAbility {

    private static final FilterCreaturePermanent soulbondFilter = new FilterCreaturePermanent();

    static {
        soulbondFilter.add(PairedPredicate.UNPAIRED);
        soulbondFilter.add(TargetController.YOU.getControllerPredicate());
        soulbondFilter.add(AnotherPredicate.instance);
    }

    SoulbondEntersOtherAbility() {
        super(Zone.BATTLEFIELD, new SoulbondEntersOtherEffect(), soulbondFilter, true, SetTargetPointer.PERMANENT);
        setRuleVisible(false);
    }

    private SoulbondEntersOtherAbility(SoulbondEntersOtherAbility ability) {
        super(ability);
    }

    @Override
    public String getRule() {
        return "Soulbond <i>(You may pair this creature with another unpaired creature when either enters the battlefield. They remain paired for as long as you control both of them.)</i>";
    }

    @Override
    public boolean checkInterveningIfClause(Game game) {
        // if you control both that creature and this one and both are unpaired
        Permanent sourcePerm = game.getPermanent(getSourceId());
        if (sourcePerm == null
                || !sourcePerm.isControlledBy(getControllerId())
                || !sourcePerm.isCreature(game)
                || sourcePerm.getPairedCard() != null) {
            return false;
        }
        // no event param to check the other creature specifically
        // at least check that a valid creature exists
        // the effect will still fizzle if not valid
        return game.getBattlefield().count(soulbondFilter, getControllerId(), this, game) > 0;
    }

    @Override
    public SoulbondEntersOtherAbility copy() {
        return new SoulbondEntersOtherAbility(this);
    }

}

class SoulbondEntersOtherEffect extends OneShotEffect {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("another not paired creature you control");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(PairedPredicate.UNPAIRED);
    }

    SoulbondEntersOtherEffect() {
        super(Outcome.Benefit);
    }

    private SoulbondEntersOtherEffect(final SoulbondEntersOtherEffect effect) {
        super(effect);
    }

    @Override
    public SoulbondEntersOtherEffect copy() {
        return new SoulbondEntersOtherEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourcePerm = source.getSourcePermanentIfItStillExists(game);
        if (sourcePerm == null
                || !sourcePerm.isControlledBy(source.getControllerId())
                || !sourcePerm.isCreature(game)
                || sourcePerm.getPairedCard() != null) {
            return false;
        }
        Permanent enteringPermanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (enteringPermanent == null
                || !enteringPermanent.isControlledBy(source.getControllerId())
                || !enteringPermanent.isCreature(game)
                || enteringPermanent.getPairedCard() != null) {
            return false;
        }
        enteringPermanent.setPairedCard(new MageObjectReference(sourcePerm, game));
        sourcePerm.setPairedCard(new MageObjectReference(enteringPermanent, game));
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            game.informPlayers(controller.getLogName() + " soulbonds " + sourcePerm.getLogName() + " with " + enteringPermanent.getLogName());
        }
        return true;
    }

}

// See state based actions check for clearing paired status

enum PairedPredicate implements Predicate<Permanent> {
    PAIRED(true),
    UNPAIRED(false);

    private final boolean paired;

    PairedPredicate(boolean paired) {
        this.paired = paired;
    }

    @Override
    public boolean apply(Permanent input, Game game) {
        return paired == (input.getPairedCard() != null);
    }

    @Override
    public String toString() {
        return paired ? "Paired" : "Unpaired";
    }

}
