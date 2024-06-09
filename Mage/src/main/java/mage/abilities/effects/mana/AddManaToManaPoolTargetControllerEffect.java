package mage.abilities.effects.mana;

import mage.Mana;
import mage.abilities.Ability;
import mage.game.Game;
import mage.players.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LevelX2
 */
public class AddManaToManaPoolTargetControllerEffect extends ManaEffect {

    protected Mana mana;
    protected boolean emptyOnlyOnTurnsEnd;

    public AddManaToManaPoolTargetControllerEffect(Mana mana, String textManaPoolOwner) {
        this(mana, textManaPoolOwner, false);
    }

    /**
     * Adds mana to the mana pool of target pointer player
     *
     * @param mana              mana that will be added to the pool
     * @param textManaPoolOwner text that references to the mana pool owner
     *                          (e.g. "damaged player's")
     * @param emptyOnTurnsEnd   if set, the mana will empty only on end of
     *                          turnstep
     */
    public AddManaToManaPoolTargetControllerEffect(Mana mana, String textManaPoolOwner, boolean emptyOnTurnsEnd) {
        super();
        this.mana = mana;
        this.emptyOnlyOnTurnsEnd = emptyOnTurnsEnd;
        this.staticText = (textManaPoolOwner.equals("their") ? "that player adds " : "add ") + mana.toString();
    }

    protected AddManaToManaPoolTargetControllerEffect(final AddManaToManaPoolTargetControllerEffect effect) {
        super(effect);
        this.mana = effect.mana.copy();
        this.emptyOnlyOnTurnsEnd = effect.emptyOnlyOnTurnsEnd;
    }

    @Override
    public AddManaToManaPoolTargetControllerEffect copy() {
        return new AddManaToManaPoolTargetControllerEffect(this);
    }

    @Override
    public Player getPlayer(Game game, Ability source) {
        return game.getPlayer(getTargetPointer().getFirst(game, source));
    }

    @Override
    public List<Mana> getNetMana(Game game, Ability source) {
        List<Mana> netMana = new ArrayList<>();
        netMana.add(mana.copy());
        return netMana;
    }

    @Override
    public Mana produceMana(Game game, Ability source) {
        return mana.copy();
    }

    @Override
    protected void addManaToPool(Player player, Mana manaToAdd, Game game, Ability source) {
        player.getManaPool().addMana(manaToAdd, game, source, emptyOnlyOnTurnsEnd);
    }
}
