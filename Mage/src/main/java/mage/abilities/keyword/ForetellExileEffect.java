package mage.abilities.keyword;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

import java.util.UUID;

public class ForetellExileEffect extends OneShotEffect {

    private final Card card;
    String foretellCost;
    String foretellSplitCost;

    public ForetellExileEffect(Card card, String foretellCost, String foretellSplitCost) {
        super(Outcome.Neutral);
        this.card = card;
        this.foretellCost = foretellCost;
        this.foretellSplitCost = foretellSplitCost;
    }

    protected ForetellExileEffect(final ForetellExileEffect effect) {
        super(effect);
        this.card = effect.card;
        this.foretellCost = effect.foretellCost;
        this.foretellSplitCost = effect.foretellSplitCost;
    }

    @Override
    public ForetellExileEffect copy() {
        return new ForetellExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null
                && card != null) {

            // get main card id
            UUID mainCardId = card.getMainCard().getId();

            // retrieve the exileId of the foretold card
            UUID exileId = CardUtil.getExileZoneId(mainCardId.toString() + "foretellAbility", game);

            // foretell turn number shows up on exile window
            ExileTargetEffect effect = new ExileTargetEffect(exileId, " Foretell Turn Number: " + game.getTurnNum());

            // remember turn number it was cast
            game.getState().setValue(mainCardId.toString() + "Foretell Turn Number", game.getTurnNum());

            // remember the foretell cost
            game.getState().setValue(mainCardId.toString() + "Foretell Cost", foretellCost);
            game.getState().setValue(mainCardId.toString() + "Foretell Split Cost", foretellSplitCost);

            // exile the card face-down
            effect.setWithName(false);
            effect.setTargetPointer(new FixedTarget(card.getId(), game));
            effect.apply(game, source);
            card.setFaceDown(true, game);
            game.addEffect(new ForetellAddCostEffect(new MageObjectReference(card, game)), source);
            game.fireEvent(GameEvent.getEvent(GameEvent.EventType.FORETELL, card.getId(), null, source.getControllerId()));
            return true;
        }
        return false;
    }
}
