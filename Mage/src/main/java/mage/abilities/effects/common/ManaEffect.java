
package mage.abilities.effects.common;

import java.util.ArrayList;
import java.util.List;
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

/**
 *
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

    /**
     * Creates the mana the effect can produce or if that already has happened
     * returns the mana the effect has created during its process of resolving
     *
     * @param game
     * @param source
     * @return
     */
    public Mana getMana(Game game, Ability source) {
        if (createdMana == null) {
            return createdMana = produceMana(false, game, source);
        }
        return createdMana;
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
        Mana mana = produceMana(true, game, source);
        if (mana != null) {
            netMana.add(mana);
        }
        return netMana;
    }

    /**
     * Produced the mana the effect can produce
     *
     * @param netMana true - produce the hypotetical possible mana for check of
     * possible castable spells
     * @param game
     * @param source
     * @return
     */
    public abstract Mana produceMana(boolean netMana, Game game, Ability source);

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
