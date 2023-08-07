
package mage.abilities.keyword;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.RequirementEffect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.constants.Duration;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

/**
 * 702.38. Provoke 702.38a Provoke is a triggered ability. “Provoke” means
 * “Whenever this creature attacks, you may choose to have target creature
 * defending player controls block this creature this combat if able. If you do,
 * untap that creature.” 702.38b If a creature has multiple instances of
 * provoke, each triggers separately.
 *
 * @author LevelX2
 */
public class ProvokeAbility extends AttacksTriggeredAbility {

    public ProvokeAbility() {
        this("Provoke <i>(Whenever this attacks, you may have target creature defending player controls untap and block it if able.)</i>");
    }

    public ProvokeAbility(String text) {
        super(new UntapTargetEffect(), true, text);
        this.addEffect(new ProvokeRequirementEffect());
    }

    protected ProvokeAbility(final ProvokeAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (super.checkTrigger(event, game)) {
            FilterCreaturePermanent filter = new FilterCreaturePermanent("creature defending player controls");
            UUID defendingPlayerId = game.getCombat().getDefendingPlayerId(sourceId, game);
            filter.add(new ControllerIdPredicate(defendingPlayerId));
            this.getTargets().clear();
            TargetCreaturePermanent target = new TargetCreaturePermanent(filter);
            this.addTarget(target);
            return true;
        }
        return false;
    }

    @Override
    public ProvokeAbility copy() {
        return new ProvokeAbility(this);
    }

}

class ProvokeRequirementEffect extends RequirementEffect {

    public ProvokeRequirementEffect() {
        this(Duration.EndOfTurn);
    }

    public ProvokeRequirementEffect(Duration duration) {
        super(duration);
        staticText = "and blocks {this} this turn if able";
    }

    protected ProvokeRequirementEffect(final ProvokeRequirementEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return permanent.getId().equals(targetPointer.getFirst(game, source));
    }

    @Override
    public boolean mustAttack(Game game) {
        return false;
    }

    @Override
    public boolean mustBlock(Game game) {
        return true;
    }

    @Override
    public UUID mustBlockAttacker(Ability source, Game game) {
        return source.getSourceId();
    }

    @Override
    public ProvokeRequirementEffect copy() {
        return new ProvokeRequirementEffect(this);
    }

}
