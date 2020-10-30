package mage.abilities.keyword;

import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.OptionalAdditionalModeSourceCosts;
import mage.game.Game;

/**
 * Same as KickerAbility, but can enable any number modes in spell ability
 *
 * @author JayDi85
 */
public class KickerWithAnyNumberModesAbility extends KickerAbility implements OptionalAdditionalModeSourceCosts {

    public KickerWithAnyNumberModesAbility(String manaString) {
        super(manaString);
    }

    public KickerWithAnyNumberModesAbility(Cost cost) {
        super(cost);
    }

    public KickerWithAnyNumberModesAbility(final KickerWithAnyNumberModesAbility ability) {
        super(ability);
    }

    @Override
    public void changeModes(Ability ability, Game game) {
        if (!isKicked(game, ability, "")) {
            return;
        }

        // activate any number modes
        int maxModes = ability.getModes().size();
        ability.getModes().setMinModes(0);
        ability.getModes().setMaxModes(maxModes);
    }

    @Override
    public KickerWithAnyNumberModesAbility copy() {
        return new KickerWithAnyNumberModesAbility(this);
    }

}
