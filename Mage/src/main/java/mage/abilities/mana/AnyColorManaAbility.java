package mage.abilities.mana;

import mage.Mana;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.mana.AddManaOfAnyColorEffect;
import mage.abilities.effects.mana.ManaEffect;
import mage.constants.Zone;
import mage.game.Game;

import java.util.ArrayList;
import java.util.List;

public class AnyColorManaAbility extends ActivatedManaAbilityImpl {

    public AnyColorManaAbility() {
        this(new TapSourceCost());
    }

    public AnyColorManaAbility(Cost cost) {
        this(cost, false);
    }

    public AnyColorManaAbility(Cost cost, boolean setFlag) {
        super(Zone.BATTLEFIELD, new AddManaOfAnyColorEffect(1, setFlag), cost);
        this.netMana.add(new Mana(0, 0, 0, 0, 0, 0, 1, 0));
    }

    /**
     * @param cost
     * @param netAmount dynamic value used during available mana calculation to
     *                  set the max possible amount the source can produce
     * @param setFlag
     */
    public AnyColorManaAbility(Cost cost, DynamicValue netAmount, boolean setFlag) {
        this(Zone.BATTLEFIELD, cost, netAmount, setFlag);
    }

    public AnyColorManaAbility(Zone zone, Cost cost, DynamicValue netAmount, boolean setFlag) {
        super(zone, new AddManaOfAnyColorEffect(1, netAmount, setFlag), cost);
        this.netMana.add(new Mana(0, 0, 0, 0, 0, 0, 1, 0));
    }

    public AnyColorManaAbility(final AnyColorManaAbility ability) {
        super(ability);
    }

    @Override
    public List<Mana> getNetMana(Game game) {
        if (game != null && game.inCheckPlayableState()) {
            List<Mana> dynamicNetMana = new ArrayList<>();
            for (Effect effect : getEffects()) {
                if (effect instanceof ManaEffect) {
                    List<Mana> effectNetMana = ((ManaEffect) effect).getNetMana(game, this);
                    if (effectNetMana != null) {
                        dynamicNetMana.addAll(effectNetMana);
                    }
                }
            }
            return dynamicNetMana;
        }
        return super.getNetMana(game);
    }

    @Override
    public AnyColorManaAbility copy() {
        return new AnyColorManaAbility(this);
    }
}
