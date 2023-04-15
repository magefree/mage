
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.DamageControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.constants.TargetController;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetOpponent;

/**
 *
 * @author TGower
 */
public final class SleeperAgent extends CardImpl {

    public SleeperAgent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{B}");
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.MINION);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Sleeper Agent enters the battlefield, target opponent gains control of it.
                Ability ability = new EntersBattlefieldTriggeredAbility(new SleeperAgentChangeControlEffect(), false);
                ability.addTarget(new TargetOpponent());
                this.addAbility(ability);
        // At the beginning of your upkeep, Sleeper Agent deals 2 damage to you.
                this.addAbility(new BeginningOfUpkeepTriggeredAbility(new DamageControllerEffect(2), TargetController.YOU, false));
    }

    private SleeperAgent(final SleeperAgent card) {
        super(card);
    }

    @Override
    public SleeperAgent copy() {
        return new SleeperAgent(this);
    }
}

class SleeperAgentChangeControlEffect extends ContinuousEffectImpl {

    public SleeperAgentChangeControlEffect() {
        super(Duration.Custom, Layer.ControlChangingEffects_2, SubLayer.NA, Outcome.GainControl);
        staticText = "target opponent gains control of {this}";
    }

    public SleeperAgentChangeControlEffect(final SleeperAgentChangeControlEffect effect) {
        super(effect);
    }

    @Override
    public SleeperAgentChangeControlEffect copy() {
        return new SleeperAgentChangeControlEffect(this);
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


