package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.replacement.EntersWithContinuousEffectsAppliedEffect;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author xenohedron
 */
public class PutOntoBattlefieldTargetEffect extends OneShotEffect {

    private final boolean tapped;
    private final List<ContinuousEffect> effects = new ArrayList<>();
    private String description;

    /**
     * Put [target card in a graveyard] onto the battlefield under your control
     */
    public PutOntoBattlefieldTargetEffect(boolean tapped) {
        super(Outcome.PutCreatureInPlay);
        this.tapped = tapped;
    }

    protected PutOntoBattlefieldTargetEffect(final PutOntoBattlefieldTargetEffect effect) {
        super(effect);
        this.tapped = effect.tapped;
        this.effects.addAll(effect.effects);
        this.description = effect.description;
    }

    @Override
    public PutOntoBattlefieldTargetEffect copy() {
        return new PutOntoBattlefieldTargetEffect(this);
    }

    /**
     * These effects are applied as the permanent enters the battlefield. Use Duration.Custom
     */
    public PutOntoBattlefieldTargetEffect withContinuousEffects(String description, ContinuousEffect... effects) {
        this.description = description;
        this.effects.addAll(Arrays.asList(effects));
        return this;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Set<Card> cardsToMove = getTargetPointer()
                .getTargets(game, source)
                .stream()
                .map(game::getCard)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        if (cardsToMove.isEmpty()) {
            return false;
        }
        if (!effects.isEmpty()) {
            game.addEffect(new EntersWithContinuousEffectsAppliedEffect(effects)
                    .setTargetPointer(this.getTargetPointer().copy()), source);
        }
        controller.moveCards(cardsToMove, Zone.BATTLEFIELD, source, game, tapped, false, false, null);
        return true;
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        return "put " + getTargetPointer().describeTargets(mode.getTargets(), "that card")
                + " onto the battlefield" + (tapped ? " tapped " : " ") + "under your control"
                + (description == null || description.isEmpty() ? "" : ". " + description);
    }
}
