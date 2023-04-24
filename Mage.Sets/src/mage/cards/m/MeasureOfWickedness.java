
package mage.cards.m;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.PutCardIntoGraveFromAnywhereAllTriggeredAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetOpponent;

/**
 *
 * @author LevelX2
 */
public final class MeasureOfWickedness extends CardImpl {

    private static final FilterCard filter = new FilterCard("another card");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public MeasureOfWickedness(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{3}{B}");


        // At the beginning of your end step, sacrifice Measure of Wickedness and you lose 8 life.        
        Ability ability = new BeginningOfEndStepTriggeredAbility(Zone.BATTLEFIELD, new SacrificeSourceEffect(), TargetController.YOU, null, false);
        Effect effect = new LoseLifeSourceControllerEffect(8);
        effect.setText("and you lose 8 life");
        ability.addEffect(effect);
        this.addAbility(ability);

        // Whenever another card is put into your graveyard from anywhere, target opponent gains control of Measure of Wickedness.
        ability = new PutCardIntoGraveFromAnywhereAllTriggeredAbility(
                new MeasureOfWickednessControlSourceEffect(), false, filter, TargetController.YOU);
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);

    }

    private MeasureOfWickedness(final MeasureOfWickedness card) {
        super(card);
    }

    @Override
    public MeasureOfWickedness copy() {
        return new MeasureOfWickedness(this);
    }
}

class MeasureOfWickednessControlSourceEffect extends ContinuousEffectImpl {

    public MeasureOfWickednessControlSourceEffect() {
        super(Duration.Custom, Layer.ControlChangingEffects_2, SubLayer.NA, Outcome.GainControl);
        staticText = "target opponent gains control of {this}";
    }

    public MeasureOfWickednessControlSourceEffect(final MeasureOfWickednessControlSourceEffect effect) {
        super(effect);
    }

    @Override
    public MeasureOfWickednessControlSourceEffect copy() {
        return new MeasureOfWickednessControlSourceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetOpponent = game.getPlayer(source.getFirstTarget());
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (permanent != null && targetOpponent != null) {
                permanent.changeControllerId(targetOpponent.getId(), game, source);
        } else {
            // no valid target exists, effect can be discarded
            discard();
        }
        return true;
    }
}
