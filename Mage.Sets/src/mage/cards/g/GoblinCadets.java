package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BlocksOrBlockedSourceTriggeredAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetOpponent;

/**
 *
 * @author LevelX2
 */
public final class GoblinCadets extends CardImpl {

    public GoblinCadets(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{R}");
        this.subtype.add(SubType.GOBLIN);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Whenever Goblin Cadets blocks or becomes blocked, target opponent gains control of it.
        Ability ability = new BlocksOrBlockedSourceTriggeredAbility(new GoblinCadetsChangeControlEffect());
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);

    }

    private GoblinCadets(final GoblinCadets card) {
        super(card);
    }

    @Override
    public GoblinCadets copy() {
        return new GoblinCadets(this);
    }
}

class GoblinCadetsChangeControlEffect extends ContinuousEffectImpl {

    public GoblinCadetsChangeControlEffect() {
        super(Duration.Custom, Layer.ControlChangingEffects_2, SubLayer.NA, Outcome.GainControl);
        staticText = "target opponent gains control of it. <i>(This removes {this} from combat.)</i>";
    }

    public GoblinCadetsChangeControlEffect(final GoblinCadetsChangeControlEffect effect) {
        super(effect);
    }

    @Override
    public GoblinCadetsChangeControlEffect copy() {
        return new GoblinCadetsChangeControlEffect(this);
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
