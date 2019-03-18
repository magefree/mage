package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.MageSingleton;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class ExileSpellEffect extends OneShotEffect implements MageSingleton {

    private static final ExileSpellEffect instance = new ExileSpellEffect();

    public static ExileSpellEffect getInstance() {
        return instance;
    }

    private ExileSpellEffect() {
        super(Outcome.Exile);
        staticText = "Exile {this}";
    }

    @Override
    public ExileSpellEffect copy() {
        return instance;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Spell spell = game.getStack().getSpell(source.getId());
            if (spell != null && !spell.isCopy()) {
                Card spellCard = spell.getCard();
                if (spellCard != null) {
                    controller.moveCards(spellCard, Zone.EXILED, source, game);
                }
            }
            return true;
        }
        return false;
    }
}
