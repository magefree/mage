
package mage.cards.p;

import java.util.UUID;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.delayed.AtTheBeginOfMainPhaseDelayedTriggeredAbility;
import mage.abilities.common.delayed.AtTheBeginOfMainPhaseDelayedTriggeredAbility.PhaseSelection;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ManaEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.ChoiceColor;
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
public final class PlasmCapture extends CardImpl {

    public PlasmCapture(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{G}{G}{U}{U}");

        // Counter target spell. At the beginning of your next precombat main phase, add X mana in any combination of colors, where X is that spell's converted mana cost.
        this.getSpellAbility().addTarget(new TargetSpell());
        this.getSpellAbility().addEffect(new PlasmCaptureCounterEffect());
    }

    public PlasmCapture(final PlasmCapture card) {
        super(card);
    }

    @Override
    public PlasmCapture copy() {
        return new PlasmCapture(this);
    }
}

class PlasmCaptureCounterEffect extends OneShotEffect {

    public PlasmCaptureCounterEffect() {
        super(Outcome.Benefit);
        this.staticText = "Counter target spell. At the beginning of your next precombat main phase, add X mana in any combination of colors, where X is that spell's converted mana cost";
    }

    public PlasmCaptureCounterEffect(final PlasmCaptureCounterEffect effect) {
        super(effect);
    }

    @Override
    public PlasmCaptureCounterEffect copy() {
        return new PlasmCaptureCounterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell = game.getStack().getSpell(getTargetPointer().getFirst(game, source));
        if (spell != null) {
            game.getStack().counter(getTargetPointer().getFirst(game, source), source.getSourceId(), game);
            // mana gets added also if counter is not successful
            int mana = spell.getConvertedManaCost();
            AtTheBeginOfMainPhaseDelayedTriggeredAbility delayedAbility
                    = new AtTheBeginOfMainPhaseDelayedTriggeredAbility(new PlasmCaptureManaEffect(mana), false, TargetController.YOU, PhaseSelection.NEXT_PRECOMBAT_MAIN);
            game.addDelayedTriggeredAbility(delayedAbility, source);
            return true;
        }
        return false;
    }
}

class PlasmCaptureManaEffect extends ManaEffect {

    int amountOfMana;

    public PlasmCaptureManaEffect(int amountOfMana) {
        super();
        this.amountOfMana = amountOfMana;
        this.staticText = "add X mana in any combination of colors, where X is that spell's converted mana cost";
    }

    public PlasmCaptureManaEffect(final PlasmCaptureManaEffect effect) {
        super(effect);
        this.amountOfMana = effect.amountOfMana;
    }

    @Override
    public PlasmCaptureManaEffect copy() {
        return new PlasmCaptureManaEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            controller.getManaPool().addMana(getMana(game, source), game, source);
            return true;
        }
        return false;
    }

    @Override
    public Mana produceMana(boolean netMana, Game game, Ability source) {
        if (netMana) {
            return new Mana(0, 0, 0, 0, 0, 0, amountOfMana, 0);
        }
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            Mana mana = new Mana();
            for (int i = 0; i < amountOfMana; i++) {
                ChoiceColor choiceColor = new ChoiceColor();
                if (!player.choose(Outcome.Benefit, choiceColor, game)) {
                    return null;
                }
                choiceColor.increaseMana(mana);
            }
            player.getManaPool().addMana(mana, game, source);
            return mana;

        }
        return null;
    }

}
