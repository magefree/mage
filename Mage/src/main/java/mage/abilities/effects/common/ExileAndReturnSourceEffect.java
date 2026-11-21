package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.constants.Pronoun;
import mage.constants.PutCards;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 * @author LevelX2
 */
public class ExileAndReturnSourceEffect extends ReturnSourceThenEffect {

    private final boolean returnUnderYourControl;
    private final PutCards putCards;

    public ExileAndReturnSourceEffect(PutCards putCards) {
        this(putCards, Pronoun.IT);
    }

    public ExileAndReturnSourceEffect(PutCards putCards, Pronoun pronoun) {
        this(putCards, pronoun, false);
    }

    /**
     * @param returnUnderYourControl return under your or owner control
     */
    public ExileAndReturnSourceEffect(PutCards putCards, Pronoun pronoun, boolean returnUnderYourControl) {
        super(Outcome.Benefit);
        this.putCards = putCards;
        this.returnUnderYourControl = returnUnderYourControl;
        this.staticText = "exile {this}, then return " + pronoun.getObjective() + ' ' +
                putCards.getMessage(false, false).replace("onto", "to") + " under " +
                (returnUnderYourControl ? "your" : pronoun.getPossessive() + " owner's") + " control";
    }

    protected ExileAndReturnSourceEffect(final ExileAndReturnSourceEffect effect) {
        super(effect);
        this.putCards = effect.putCards;
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
        Card card = permanent.getMainCard();
        controller.moveCards(permanent, Zone.EXILED, source, game);
        game.processAction();
        // only the card this exiled comes back; if a replacement put it anywhere else, there's nothing to return
        if (game.getState().getZone(card.getId()) != Zone.EXILED) {
            return true;
        }
        putCards.moveCard(
                returnUnderYourControl ? controller : game.getPlayer(permanent.getOwnerId()),
                card, source, game, "card"
        );
        Permanent returned = putCards.getPermanentPutOntoBattlefield(card, game);
        if (returned != null) {
            resolveAdditionalEffects(game, source, returned);
        }
        return true;
    }
}
