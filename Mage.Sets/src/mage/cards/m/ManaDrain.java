
package mage.cards.m;

import java.util.UUID;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.delayed.AtTheBeginOfMainPhaseDelayedTriggeredAbility;
import mage.abilities.common.delayed.AtTheBeginOfMainPhaseDelayedTriggeredAbility.PhaseSelection;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.mana.AddManaToManaPoolTargetControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.target.TargetSpell;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public final class ManaDrain extends CardImpl {

    public ManaDrain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{U}{U}");

        // Counter target spell. At the beginning of your next main phase, add X mana of {C}, where X is that spell's converted mana cost.
        this.getSpellAbility().addTarget(new TargetSpell());
        this.getSpellAbility().addEffect(new ManaDrainCounterEffect());
    }

    private ManaDrain(final ManaDrain card) {
        super(card);
    }

    @Override
    public ManaDrain copy() {
        return new ManaDrain(this);
    }
}

class ManaDrainCounterEffect extends OneShotEffect {

    public ManaDrainCounterEffect() {
        super(Outcome.Benefit);
        this.staticText = "Counter target spell. At the beginning of your next main phase, add an amount of {C} equal to that spell's mana value";
    }

    private ManaDrainCounterEffect(final ManaDrainCounterEffect effect) {
        super(effect);
    }

    @Override
    public ManaDrainCounterEffect copy() {
        return new ManaDrainCounterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell = game.getStack().getSpell(getTargetPointer().getFirst(game, source));
        if (spell != null) {
            game.getStack().counter(getTargetPointer().getFirst(game, source), source, game);
            // mana gets added also if counter is not successful
            int cmc = spell.getManaValue();
            Effect effect = new AddManaToManaPoolTargetControllerEffect(Mana.ColorlessMana(cmc), "your");
            effect.setTargetPointer(new FixedTarget(source.getControllerId()));
            AtTheBeginOfMainPhaseDelayedTriggeredAbility delayedAbility
                    = new AtTheBeginOfMainPhaseDelayedTriggeredAbility(effect, false, TargetController.YOU, PhaseSelection.NEXT_MAIN);
            game.addDelayedTriggeredAbility(delayedAbility, source);
            return true;
        }
        return false;
    }
}
