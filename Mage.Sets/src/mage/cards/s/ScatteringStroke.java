
package mage.cards.s;

import java.util.UUID;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.delayed.AtTheBeginOfMainPhaseDelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.mana.AddManaToManaPoolSourceControllerEffect;
import mage.abilities.effects.common.ClashEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.TargetSpell;

/**
 *
 * @author LevelX2
 */
public final class ScatteringStroke extends CardImpl {

    public ScatteringStroke(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{U}{U}");

        // Counter target spell. Clash with an opponent. If you win, at the beginning of your next main phase, you may add {X}, where X is that spell's converted mana cost.
        this.getSpellAbility().addEffect(new ScatteringStrokeEffect());
        this.getSpellAbility().addTarget(new TargetSpell());
    }

    private ScatteringStroke(final ScatteringStroke card) {
        super(card);
    }

    @Override
    public ScatteringStroke copy() {
        return new ScatteringStroke(this);
    }
}

class ScatteringStrokeEffect extends OneShotEffect {

    public ScatteringStrokeEffect() {
        super(Outcome.Benefit);
        this.staticText = "Counter target spell. Clash with an opponent. If you win, at the beginning of your next main phase, you may add an amount of {C} equal to that spell's mana value";
    }

    public ScatteringStrokeEffect(final ScatteringStrokeEffect effect) {
        super(effect);
    }

    @Override
    public ScatteringStrokeEffect copy() {
        return new ScatteringStrokeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell = (Spell) game.getStack().getStackObject(getTargetPointer().getFirst(game, source));
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null && spell != null) {
            game.getStack().counter(spell.getId(), source, game);
            if (new ClashEffect().apply(game, source)) {
                Effect effect = new AddManaToManaPoolSourceControllerEffect(new Mana(0, 0, 0, 0, 0, 0, 0, spell.getManaValue()));
                AtTheBeginOfMainPhaseDelayedTriggeredAbility delayedAbility
                        = new AtTheBeginOfMainPhaseDelayedTriggeredAbility(effect, true, TargetController.YOU, AtTheBeginOfMainPhaseDelayedTriggeredAbility.PhaseSelection.NEXT_MAIN);
                game.addDelayedTriggeredAbility(delayedAbility, source);
            }
            return true;
        }
        return false;
    }
}
