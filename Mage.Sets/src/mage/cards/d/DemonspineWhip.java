
package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.util.CardUtil;

import java.util.UUID;

/**
 *
 * @author jeffwadsworth
 */
public final class DemonspineWhip extends CardImpl {

    public DemonspineWhip(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{B}{R}");
        this.subtype.add(SubType.EQUIPMENT);

        // {X}: Equipped creature gets +X/+0 until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostEquippedEffect(new XPaid(), StaticValue.get(0), Duration.EndOfTurn), new ManaCostsImpl<>("{X}")));

        // Equip {1}
        this.addAbility(new EquipAbility(Outcome.AddAbility, new GenericManaCost(1), false));
    }

    private DemonspineWhip(final DemonspineWhip card) {
        super(card);
    }

    @Override
    public DemonspineWhip copy() {
        return new DemonspineWhip(this);
    }
}

class XPaid implements DynamicValue {

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        int paid = CardUtil.getSourceCostsTag(game, sourceAbility, "X", 0);
        return paid;
    }

    @Override
    public DynamicValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "";
    }

    @Override
    public String toString() {
        return "X";
    }
}
