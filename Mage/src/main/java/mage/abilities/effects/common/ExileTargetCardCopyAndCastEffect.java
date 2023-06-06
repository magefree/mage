package mage.abilities.effects.common;

import mage.ApprovingObject;
import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

public class ExileTargetCardCopyAndCastEffect extends OneShotEffect {

    private final boolean optional;

    public ExileTargetCardCopyAndCastEffect() {
        this(true);
    }

    public ExileTargetCardCopyAndCastEffect(boolean optional) {
        super(Outcome.PlayForFree);

        this.optional = optional;
    }

    public ExileTargetCardCopyAndCastEffect(final ExileTargetCardCopyAndCastEffect effect) {
        super(effect);

        this.optional = effect.optional;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Card card = game.getCard(getTargetPointer().getFirst(game, source));
        if (player == null || card == null) {
            return false;
        }
        player.moveCards(card, Zone.EXILED, source, game);
        Card cardCopy = game.copyCard(card, source, source.getControllerId());
        if (optional && !player.chooseUse(outcome, "Cast copy of " +
                card.getName() + " without paying its mana cost?", source, game)) {
            return true;
        }
        game.getState().setValue("PlayFromNotOwnHandZone" + cardCopy.getId(), Boolean.TRUE);
        player.cast(
                player.chooseAbilityForCast(cardCopy, game, true),
                game, true, new ApprovingObject(source, game));
        game.getState().setValue("PlayFromNotOwnHandZone" + cardCopy.getId(), null);
        return true;
    }

    @Override
    public Effect copy() {
        return new ExileTargetCardCopyAndCastEffect(this);
    }

}
