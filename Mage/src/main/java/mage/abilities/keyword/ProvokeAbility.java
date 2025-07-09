
package mage.abilities.keyword;

import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.RequirementEffect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.constants.Duration;
import mage.constants.SetTargetPointer;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.targetadjustment.ThatPlayerControlsTargetAdjuster;

import java.util.UUID;

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
        super(new UntapTargetEffect(), true, text, SetTargetPointer.PLAYER);
        this.addEffect(new ProvokeRequirementEffect());
        this.addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_CREATURE));
        this.setTargetAdjuster(new ThatPlayerControlsTargetAdjuster());
    }

    protected ProvokeAbility(final ProvokeAbility ability) {
        super(ability);
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
        return permanent.getId().equals(getTargetPointer().getFirst(game, source));
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
