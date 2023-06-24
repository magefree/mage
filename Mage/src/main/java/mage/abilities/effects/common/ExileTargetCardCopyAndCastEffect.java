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

// Author: alexander-novo
// An effect for cards which instruct you to exile a card, then copy that card, and cast it.
// NOTE: You must set effect text on your own
public class ExileTargetCardCopyAndCastEffect extends OneShotEffect {

    private final boolean optional;
    private final boolean noMana;

    public ExileTargetCardCopyAndCastEffect(boolean noMana) {
        this(noMana, true);
    }

    /**
     * NOTE: You must supply your own effect text
     * @param noMana   Whether the copy can be cast without paying its mana cost
     * @param optional Whether the casting of the copy is optional (otherwise it must be cast if possible)
     */
    public ExileTargetCardCopyAndCastEffect(boolean noMana, boolean optional) {
        super(Outcome.PlayForFree);

        this.optional = optional;
        this.noMana = noMana;
    }

    public ExileTargetCardCopyAndCastEffect(final ExileTargetCardCopyAndCastEffect effect) {
        super(effect);

        this.optional = effect.optional;
        this.noMana = effect.noMana;
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
                player.chooseAbilityForCast(cardCopy, game, this.noMana),
                game, this.noMana, new ApprovingObject(source, game));
        game.getState().setValue("PlayFromNotOwnHandZone" + cardCopy.getId(), null);
        return true;
    }

    @Override
    public ExileTargetCardCopyAndCastEffect copy() {
        return new ExileTargetCardCopyAndCastEffect(this);
    }

}
