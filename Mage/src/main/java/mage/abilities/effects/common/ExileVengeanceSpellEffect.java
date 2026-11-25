package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.MageSingleton;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.VengeanceSpellCard;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;

/**
 * Exiles the whole Vengeance card as its spell resolves.
 */
public class ExileVengeanceSpellEffect extends OneShotEffect implements MageSingleton {

    private static final ExileVengeanceSpellEffect instance = new ExileVengeanceSpellEffect();

    public static ExileVengeanceSpellEffect getInstance() {
        return instance;
    }

    private ExileVengeanceSpellEffect() {
        super(Outcome.Exile);
        staticText = "";
    }

    @Override
    public ExileVengeanceSpellEffect copy() {
        return instance;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Spell spell = game.getStack().getSpell(source.getId());
        if (spell != null && spell.getCard() instanceof VengeanceSpellCard) {
            Card parentCard = ((VengeanceSpellCard) spell.getCard()).getParentCard();
            controller.moveCardsToExile(parentCard, source, game, true, null, "");
        }
        return true;
    }
}
