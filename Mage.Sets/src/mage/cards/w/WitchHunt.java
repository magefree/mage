
package mage.cards.w;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.DamageControllerEffect;
import mage.abilities.effects.common.continuous.CantGainLifeAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.Target;
import mage.target.common.TargetOpponent;

/**
 *
 * @author LevelX2
 */
public final class WitchHunt extends CardImpl {

    public WitchHunt(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{4}{R}");

        // Players can't gain life.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CantGainLifeAllEffect()));
        // At the beginning of your upkeep, Witch Hunt deals 4 damage to you.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new DamageControllerEffect(4), TargetController.YOU, false));
        // At the beginning of your end step, target opponent chosen at random gains control of Witch Hunt.
        Ability ability = new BeginningOfEndStepTriggeredAbility(Zone.BATTLEFIELD, new WitchHuntEffect(), TargetController.YOU, null, false);
        Target target = new TargetOpponent();
        target.setRandom(true);
        ability.addTarget(target);
        this.addAbility(ability);
    }

    private WitchHunt(final WitchHunt card) {
        super(card);
    }

    @Override
    public WitchHunt copy() {
        return new WitchHunt(this);
    }
}

class WitchHuntEffect extends ContinuousEffectImpl {

    public WitchHuntEffect() {
        super(Duration.Custom, Layer.ControlChangingEffects_2, SubLayer.NA, Outcome.GainControl);
        staticText = "target opponent chosen at random gains control of {this}";
    }

    private WitchHuntEffect(final WitchHuntEffect effect) {
        super(effect);
    }

    @Override
    public WitchHuntEffect copy() {
        return new WitchHuntEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null) {
            return permanent.changeControllerId(this.getTargetPointer().getFirst(game, source), game, source);
        }
        return false;
    }

}
