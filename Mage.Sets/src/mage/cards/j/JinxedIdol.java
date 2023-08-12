
package mage.cards.j;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.OnEventTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.DamageControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetOpponent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class JinxedIdol extends CardImpl {

    public JinxedIdol(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // At the beginning of your upkeep, Jinxed Idol deals 2 damage to you.
        this.addAbility(new OnEventTriggeredAbility(EventType.UPKEEP_STEP_PRE, "beginning of your upkeep", new DamageControllerEffect(2)));

        // Sacrifice a creature: Target opponent gains control of Jinxed Idol.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new JinxedIdolEffect(),
                new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT));
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private JinxedIdol(final JinxedIdol card) {
        super(card);
    }

    @Override
    public JinxedIdol copy() {
        return new JinxedIdol(this);
    }

}

class JinxedIdolEffect extends ContinuousEffectImpl {

    public JinxedIdolEffect() {
        super(Duration.Custom, Layer.ControlChangingEffects_2, SubLayer.NA, Outcome.GainControl);
        staticText = "Target opponent gains control of {this}";
    }

    public JinxedIdolEffect(final JinxedIdolEffect effect) {
        super(effect);
    }

    @Override
    public JinxedIdolEffect copy() {
        return new JinxedIdolEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (permanent != null) {
            return permanent.changeControllerId(source.getFirstTarget(), game, source);
        } else {
            discard();
        }
        return false;
    }

}
