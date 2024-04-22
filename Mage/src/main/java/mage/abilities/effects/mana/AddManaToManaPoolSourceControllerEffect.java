package mage.abilities.effects.mana;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

/**
 * @author magenoxx
 */
public class AddManaToManaPoolSourceControllerEffect extends OneShotEffect {

    protected Mana mana;

    public AddManaToManaPoolSourceControllerEffect(Mana mana) {
        super(Outcome.PutManaInPool);
        this.mana = mana;
        this.staticText = "add " + mana.toString() + "";
    }

    protected AddManaToManaPoolSourceControllerEffect(final AddManaToManaPoolSourceControllerEffect effect) {
        super(effect);
        this.mana = effect.mana.copy();
    }

    @Override
    public AddManaToManaPoolSourceControllerEffect copy() {
        return new AddManaToManaPoolSourceControllerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            player.getManaPool().addMana(mana, game, source);
            return true;
        }
        return false;
    }
}
