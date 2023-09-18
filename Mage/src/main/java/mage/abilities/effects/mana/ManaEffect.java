package mage.abilities.effects.mana;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.constants.AbilityType;
import mage.constants.ManaType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.events.ManaEvent;
import mage.game.events.TappedForManaEvent;
import mage.players.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author BetaSteward_at_googlemail.com
 */
public abstract class ManaEffect extends OneShotEffect {

    public ManaEffect() {
        super(Outcome.PutManaInPool);
    }

    protected ManaEffect(final ManaEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = getPlayer(game, source);
        if (player == null) {
            return false;
        }
        if (game.inCheckPlayableState()) {
            // During calculation of the available mana for a player the "TappedForMana" event is fired to simulate triggered mana production.
            // By checking the inCheckPlayableState these events are handled to give back only the available mana of instead really producing mana
            // So it's important if ManaEffects overwrite the apply method to take care for this.
            if (source instanceof TriggeredAbility) {
                player.addAvailableTriggeredMana(getNetMana(game, source));
            }
            return true; // No need to add mana to pool during checkPlayable   
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
     * @param game   warning, can be NULL for card score calcs (AI games)
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
     * Returns the currently available max mana variations the effect can
     * produce. Also provides the possible before produced mana from other
     * abilities. Needed for some abilities that produce mana related to the
     * mana existing in the mana pool.
     *
     * @param game
     * @param possibleManaInPool The possible mana already produced by other
     *                           sources for this calculation option
     * @param source
     * @return
     */
    public List<Mana> getNetMana(Game game, Mana possibleManaInPool, Ability source) {
        return getNetMana(game, source);
    }

    /**
     * The type of mana a permanent "could produce" is the type of mana that any
     * ability of that permanent can generate, taking into account any
     * applicable replacement effects. If the type of mana can’t be defined,
     * there’s no type of mana that that permanent could produce. The "type" of
     * mana is its color, or lack thereof (for colorless mana).
     *
     * @param game
     * @param source
     * @return
     */
    public Set<ManaType> getProducableManaTypes(Game game, Ability source) {
        return ManaType.getManaTypesFromManaList(getNetMana(game, source));
    }

    /**
     * Produced the mana the effect can produce (DO NOT add it to mana pool --
     * return all added as mana object to process by replace events)
     * <p>
     * WARNING, produceMana can be called multiple times for mana and spell
     * available calculations if you don't want it then overide getNetMana to
     * return max possible mana values (if you have choose dialogs or extra
     * effects like new counters in produceMana)
     *
     * @param game   warning, can be NULL for AI score calcs (game == null)
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
        if (source.getAbilityType() == AbilityType.MANA && source.hasTapCost()) {
            ManaEvent event = new TappedForManaEvent(source.getSourceId(), source, source.getControllerId(), mana, game);
            if (!game.replaceEvent(event)) {
                game.fireEvent(event);
            }
        }
    }

    @Override
    public ManaEffect setText(String staticText) {
        super.setText(staticText);
        return this;
    }
}
