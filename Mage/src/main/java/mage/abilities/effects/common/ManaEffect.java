package mage.abilities.effects.common;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.constants.AbilityType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ManaEvent;
import mage.players.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * @author BetaSteward_at_googlemail.com
 */
public abstract class ManaEffect extends OneShotEffect {

    protected Mana createdMana;

    public ManaEffect() {
        super(Outcome.PutManaInPool);
        createdMana = null;
    }

    public ManaEffect(final ManaEffect effect) {
        super(effect);
        this.createdMana = effect.createdMana == null ? null : effect.createdMana.copy();
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = getPlayer(game, source);
        if (player == null) {
            return false;
        }
        Mana manaToAdd = produceMana(game, source);
        if (manaToAdd != null && manaToAdd.count() > 0) {
            checkToFirePossibleEvents(manaToAdd, game, source);
            addManaToPool(player, manaToAdd, game, source);
        }
        return true;
    }

    protected Player getPlayer(Game game, Ability source) {
        return game.getPlayer(source.getControllerId());
    }

    protected void addManaToPool(Player player, Mana manaToAdd, Game game, Ability source) {
        player.getManaPool().addMana(manaToAdd, game, source);
    }

    /**
     * Returns the currently available max mana variations the effect can
     * produce
     *
     * @param game
     * @param source
     * @return
     */
    public List<Mana> getNetMana(Game game, Ability source) {
        List<Mana> netMana = new ArrayList<>();
        Mana mana = produceMana(game, source);
        if (mana != null && mana.count() > 0) {
            netMana.add(mana);
        }
        return netMana;
    }

    /**
     * Produced the mana the effect can produce (DO NOT add it to mana pool -- return all added as mana object to process by replace events)
     * <p>
     * WARNING, produceMana can be called multiple times for mana and spell available calculations
     * if you don't want it then overide getNetMana to return max possible mana values
     * (if you have choose dialogs or extra effects like new counters in produceMana)
     *
     * @param game
     * @param source
     * @return can return null or empty mana
     */
    public abstract Mana produceMana(Game game, Ability source);

    /**
     * Only used for mana effects that decide which kind of mana is produced
     * during resolution of the effect.
     *
     * @param mana
     * @param game
     * @param source
     */
    public void checkToFirePossibleEvents(Mana mana, Game game, Ability source) {
        if (source.getAbilityType() == AbilityType.MANA) {
            for (Cost cost : source.getCosts()) {
                if (cost instanceof TapSourceCost) {
                    ManaEvent event = new ManaEvent(GameEvent.EventType.TAPPED_FOR_MANA, source.getSourceId(), source.getSourceId(), source.getControllerId(), mana);
                    if (!game.replaceEvent(event)) {
                        game.fireEvent(event);
                    }
                }
            }
        }
    }
}
