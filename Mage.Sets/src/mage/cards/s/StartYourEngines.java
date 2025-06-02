
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.VehiclesBecomeArtifactCreatureEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author LevelX2
 */
public final class StartYourEngines extends CardImpl {

    public StartYourEngines(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{R}");

        // Vehicles you control becomes artifact creatures until end of turn.
        Effect effect = new VehiclesBecomeArtifactCreatureEffect(Duration.EndOfTurn);
        this.getSpellAbility().addEffect(effect);

        // Creatures you control get +2/+0 until end of turn.
        this.getSpellAbility().addEffect(new BoostControlledEffect(2, 0, Duration.EndOfTurn));
    }

    private StartYourEngines(final StartYourEngines card) {
        super(card);
    }

    @Override
    public StartYourEngines copy() {
        return new StartYourEngines(this);
    }
}
