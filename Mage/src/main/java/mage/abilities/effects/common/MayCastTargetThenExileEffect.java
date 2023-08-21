package mage.abilities.effects.common;

import mage.ApprovingObject;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.replacement.ThatSpellGraveyardExileReplacementEffect;
import mage.cards.Card;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

/**
 * @author xenohedron
 */
public class MayCastTargetThenExileEffect extends OneShotEffect {

    private final Duration duration;
    private final boolean noMana;

    /**
     * Allows to cast the target card immediately, either for its cost or for free.
     * If resulting spell would be put into graveyard, exiles it instead.
     */
    public MayCastTargetThenExileEffect(boolean noMana) {
        super(Outcome.Benefit);
        this.duration = Duration.OneUse;
        this.noMana = noMana;
    }

    /**
     * Makes the target card playable for the specified duration as long as it remains in that zone.
     * If resulting spell would be put into graveyard, exiles it instead.
     */
    public MayCastTargetThenExileEffect(Duration duration) {
        super(Outcome.Benefit);
        this.duration = duration;
        this.noMana = false;
    }

    protected MayCastTargetThenExileEffect(final MayCastTargetThenExileEffect effect) {
        super(effect);
        this.duration = effect.duration;
        this.noMana = effect.noMana;
    }

    @Override
    public MayCastTargetThenExileEffect copy() {
        return new MayCastTargetThenExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getCard(getTargetPointer().getFirst(game, source));
        if (card == null) {
            return false;
        }
        FixedTarget fixedTarget = new FixedTarget(card, game);
        if (duration == Duration.OneUse) {
            Player controller = game.getPlayer(source.getControllerId());
            if (controller == null || !controller.chooseUse(outcome, "Cast " + card.getLogName() + '?', source, game)) {
                return false;
            }
            game.getState().setValue("PlayFromNotOwnHandZone" + card.getId(), Boolean.TRUE);
            controller.cast(controller.chooseAbilityForCast(card, game, noMana),
                    game, noMana, new ApprovingObject(source, game));
            game.getState().setValue("PlayFromNotOwnHandZone" + card.getId(), null);
        } else {
            CardUtil.makeCardPlayable(game, source, card, duration, false);
        }
        ContinuousEffect effect = new ThatSpellGraveyardExileReplacementEffect(true);
        effect.setTargetPointer(fixedTarget);
        game.addEffect(effect, source);
        return true;
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        String text = "you may cast " + getTargetPointer().describeTargets(mode.getTargets(), "it");
        if (duration == Duration.EndOfTurn) {
            text += " this turn";
        } else if (!duration.toString().isEmpty()) {
            text += duration.toString();
        }
        if (noMana) {
            text += " without paying its mana cost";
        }
        return text + ". " + ThatSpellGraveyardExileReplacementEffect.RULE_YOUR;
    }

}
