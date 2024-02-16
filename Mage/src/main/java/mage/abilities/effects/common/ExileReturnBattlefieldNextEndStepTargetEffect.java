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

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author xenohedron
 */
public class ExileReturnBattlefieldNextEndStepTargetEffect extends OneShotEffect {

    private boolean yourControl;
    private boolean textThatCard;
    private boolean exiledOnly;

    public ExileReturnBattlefieldNextEndStepTargetEffect() {
        super(Outcome.Neutral);
        this.yourControl = false;
        this.textThatCard = true;
        this.exiledOnly = false;
    }

    protected ExileReturnBattlefieldNextEndStepTargetEffect(final ExileReturnBattlefieldNextEndStepTargetEffect effect) {
        super(effect);
        this.yourControl = effect.yourControl;
        this.textThatCard = effect.textThatCard;
        this.exiledOnly = effect.exiledOnly;
    }

    public ExileReturnBattlefieldNextEndStepTargetEffect underYourControl(boolean yourControl) {
        this.yourControl = yourControl;
        return this;
    }

    public ExileReturnBattlefieldNextEndStepTargetEffect withTextThatCard(boolean textThatCard) {
        this.textThatCard = textThatCard;
        return this;
    }

    public ExileReturnBattlefieldNextEndStepTargetEffect returnExiledOnly(boolean exiledOnly) {
        this.exiledOnly = exiledOnly;
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
                .collect(Collectors.toCollection(LinkedHashSet::new));
        if (toExile.isEmpty()) {
            return false;
        }
        controller.moveCardsToExile(toExile, source, game, true, CardUtil.getExileZoneId(game, source), CardUtil.getSourceName(game, source));
        Effect effect = yourControl
                ? new ReturnToBattlefieldUnderYourControlTargetEffect(exiledOnly)
                : new ReturnToBattlefieldUnderOwnerControlTargetEffect(false, exiledOnly);
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
        boolean plural = getTargetPointer().isPlural(mode.getTargets());
        if (exiledOnly) {
            text += plural ? "the exiled cards" : "the exiled card";
        } else if (textThatCard) {
            text += plural ? "those cards" : "that card";
        } else {
            text += plural ? "them" : "it";
        }
        text += " to the battlefield";
        if (yourControl) {
            text += " under your control";
        } else {
            text += " under " + (plural ? "their" : "its") + " owner's control";
        }
        return text + " at the beginning of the next end step";
    }

}
