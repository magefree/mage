
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
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
        Effect effect = new StartYourEnginesEffect();
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

class StartYourEnginesEffect extends ContinuousEffectImpl {

    public StartYourEnginesEffect() {
        super(Duration.EndOfTurn, Layer.TypeChangingEffects_4, SubLayer.NA, Outcome.BecomeCreature);
        staticText = "Vehicles you control become artifact creatures until end of turn";
    }

    private StartYourEnginesEffect(final StartYourEnginesEffect effect) {
        super(effect);
    }

    @Override
    public StartYourEnginesEffect copy() {
        return new StartYourEnginesEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(source.getControllerId())) {
            if (permanent != null && permanent.hasSubtype(SubType.VEHICLE, game)) {
                if (sublayer == SubLayer.NA) {
                    permanent.addCardType(game, CardType.ARTIFACT);
                    permanent.addCardType(game, CardType.CREATURE);// TODO: Check if giving CREATURE Type is correct
                }
            }
        }
        return true;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

}
