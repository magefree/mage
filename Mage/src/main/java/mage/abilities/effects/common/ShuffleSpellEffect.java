package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.MageSingleton;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;

/**
 * @author nantuko
 */
public class ShuffleSpellEffect extends OneShotEffect implements MageSingleton {

    private static final ShuffleSpellEffect instance = new ShuffleSpellEffect();

    public static ShuffleSpellEffect getInstance() {
        return instance;
    }

    private ShuffleSpellEffect() {
        super(Outcome.Neutral);
        staticText = "Shuffle {this} into its owner's library";
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            // We have to use the spell id because in case of copied spells, the sourceId can be multiple times on the stack
            Spell spell = game.getStack().getSpell(source.getId());
            if (spell != null) {
                if (controller.moveCards(spell, Zone.LIBRARY, source, game)) {
                    Player owner = game.getPlayer(spell.getCard().getOwnerId());
                    if (owner != null) {
                        owner.shuffleLibrary(source, game);
                    }

                }
            }
            return true;
        }
        return false;
    }

    @Override
    public ShuffleSpellEffect copy() {
        return instance;
    }
}
