package mage.abilities.mana;

import java.util.ArrayList;
import java.util.List;

import mage.Mana;
import mage.abilities.costs.Cost;
import mage.abilities.decorator.ConditionalManaEffect;
import mage.constants.Zone;
import mage.game.Game;

/**
 * @author LevelX2
 */
public class ConditionalManaAbility extends ActivatedManaAbilityImpl {

    ConditionalManaEffect conditionalManaEffect;

    public ConditionalManaAbility(Zone zone, ConditionalManaEffect effect, Cost cost) {
        super(zone, effect, cost);
        this.conditionalManaEffect = effect;
    }

    protected ConditionalManaAbility(final ConditionalManaAbility ability) {
        super(ability);
        this.conditionalManaEffect = ability.conditionalManaEffect;
    }

    @Override
    public ConditionalManaAbility copy() {
        return new ConditionalManaAbility(this);
    }

    @Override
    public List<Mana> getNetMana(Game game) {
        return new ArrayList<>(conditionalManaEffect.getNetMana(game, this));
    }
}
