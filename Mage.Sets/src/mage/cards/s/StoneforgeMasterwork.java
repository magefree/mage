
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author LevelX2
 */
public final class StoneforgeMasterwork extends CardImpl {

    public StoneforgeMasterwork(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +1/+1 for each other creature you control that shares a creature type with it.
        StoneforgeMasterworkDynamicValue countEnchantments = new StoneforgeMasterworkDynamicValue();
        Effect effect = new BoostEquippedEffect(countEnchantments, countEnchantments);
        effect.setText("Equipped creature gets +1/+1 for each other creature you control that shares a creature type with it");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));

        // Equip {2}
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new GenericManaCost(2), false));
    }

    private StoneforgeMasterwork(final StoneforgeMasterwork card) {
        super(card);
    }

    @Override
    public StoneforgeMasterwork copy() {
        return new StoneforgeMasterwork(this);
    }
}

class StoneforgeMasterworkDynamicValue implements DynamicValue {

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        Permanent equipment = game.getPermanent(sourceAbility.getSourceId());
        int xValue = 0;
        if (equipment != null && equipment.getAttachedTo() != null) {
            Permanent equipped = game.getPermanent(equipment.getAttachedTo());
            if (equipped != null) {
                for (Permanent permanent : game.getBattlefield().getAllActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, sourceAbility.getControllerId(), game)) {
                    if (!permanent.getId().equals(equipped.getId())) {
                        if (equipped.shareCreatureTypes(game, permanent)) {
                            xValue++;
                        }
                    }
                }
            }
        }
        return xValue;
    }

    @Override
    public StoneforgeMasterworkDynamicValue copy() {
        return new StoneforgeMasterworkDynamicValue();
    }

    @Override
    public String toString() {
        return "X";
    }

    @Override
    public String getMessage() {
        return "other creature you control that shares a creature type with it";
    }
}
