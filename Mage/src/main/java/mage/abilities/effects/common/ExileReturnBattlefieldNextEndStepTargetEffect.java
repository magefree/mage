package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardsImpl;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.target.targetpointer.FixedTargets;
import mage.util.CardUtil;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author xenohedron
 */
public class ExileReturnBattlefieldNextEndStepTargetEffect extends OneShotEffect {

    private boolean yourControl;

    public ExileReturnBattlefieldNextEndStepTargetEffect() {
        super(Outcome.Neutral);
    }

    protected ExileReturnBattlefieldNextEndStepTargetEffect(final ExileReturnBattlefieldNextEndStepTargetEffect effect) {
        super(effect);
        this.yourControl = effect.yourControl;
    }

    public ExileReturnBattlefieldNextEndStepTargetEffect underYourControl(boolean yourControl) {
        this.yourControl = yourControl;
        return this;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Set<Card> toExile = getTargetPointer().getTargets(game, source)
                .stream()
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        if (toExile.isEmpty()) {
            return false;
        }
        controller.moveCardsToExile(toExile, source, game, true, CardUtil.getExileZoneId(game, source), CardUtil.getSourceName(game, source));
        Effect effect = yourControl
                ? new ReturnToBattlefieldUnderYourControlTargetEffect()
                : new ReturnToBattlefieldUnderOwnerControlTargetEffect(false, false);
        effect.setTargetPointer(new FixedTargets(new CardsImpl(toExile), game));
        game.addDelayedTriggeredAbility(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(effect), source);
        return true;
    }

    @Override
    public ExileReturnBattlefieldNextEndStepTargetEffect copy() {
        return new ExileReturnBattlefieldNextEndStepTargetEffect(this);
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        String text = "exile " + getTargetPointer().describeTargets(mode.getTargets(), "that creature") + ". Return ";
        if (yourControl) {
            text += "it to the battlefield under your";
        } else if (getTargetPointer().isPlural(mode.getTargets())) {
            text += "those cards to the battlefield under their owner's";
        } else {
            text += "that card to the battlefield under its owner's";
        }
        return text + " control at the beginning of the next end step";
    }

}
