package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.Pronoun;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.constants.PutCards;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 * @author LevelX2
 */
public class ExileAndReturnSourceEffect extends OneShotEffect {

    private final Pronoun pronoun;
    private final Effect additionalEffect;
    private final boolean returnUnderYourControl;
    private final PutCards putCards;

    public ExileAndReturnSourceEffect(PutCards putCards) {
        this(putCards, Pronoun.IT);
    }

    public ExileAndReturnSourceEffect(PutCards putCards, Pronoun pronoun) {
        this(putCards, pronoun, false);
    }

    public ExileAndReturnSourceEffect(PutCards putCards, Pronoun pronoun, boolean returnUnderYourControl) {
        this(putCards, pronoun, returnUnderYourControl, null);
    }

    /**
     * @param pronoun
     * @param additionalEffect       that effect is applies as source is exiled
     * @param returnUnderYourControl return under your or owner control
     */
    public ExileAndReturnSourceEffect(PutCards putCards, Pronoun pronoun, boolean returnUnderYourControl, Effect additionalEffect) {
        super(Outcome.Benefit);
        this.pronoun = pronoun;
        this.putCards = putCards;
        this.additionalEffect = additionalEffect;
        this.returnUnderYourControl = returnUnderYourControl;
    }

    protected ExileAndReturnSourceEffect(final ExileAndReturnSourceEffect effect) {
        super(effect);
        this.putCards = effect.putCards;
        this.pronoun = effect.pronoun;
        this.additionalEffect = effect.additionalEffect;
        this.returnUnderYourControl = effect.returnUnderYourControl;
    }

    @Override
    public ExileAndReturnSourceEffect copy() {
        return new ExileAndReturnSourceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        // Creature has to be on the battlefield to get exiled and be able to return transformed
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (permanent == null || controller == null) {
            return true;
        }
        controller.moveCards(permanent, Zone.EXILED, source, game);
        putCards.moveCard(
                returnUnderYourControl ? controller : game.getPlayer(permanent.getOwnerId()),
                permanent.getMainCard(), source, game, "card"
        );
        if (additionalEffect == null || game.getPermanent(permanent.getId()) == null) {
            return true;
        }
        if (additionalEffect instanceof ContinuousEffect) {
            game.addEffect((ContinuousEffect) additionalEffect, source);
        } else {
            additionalEffect.apply(game, source);
        }
        return true;
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        return "exile {this}, then return " + pronoun.getObjective() + ' ' +
                putCards.getMessage(false, false).replace("onto", "to") + " under " +
                (returnUnderYourControl ? "your" : pronoun.getPossessive() + " owner's") + " control"
                + (additionalEffect == null ? "" : ". If you do, " + additionalEffect.getText(mode));
    }
}
