
package mage.cards.d;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;

/**
 *
 * @author LevelX2
 */
public final class DragonThroneOfTarkir extends CardImpl {

    public DragonThroneOfTarkir(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{4}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature has defender and "{2}, {T}: Other creatures you control gain trample and get +X/+X until end of turn, where X is this creature's power."
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAttachedEffect(DefenderAbility.getInstance(), AttachmentType.EQUIPMENT));
        Effect effect = new GainAbilityControlledEffect(TrampleAbility.getInstance(), Duration.EndOfTurn, StaticFilters.FILTER_PERMANENT_CREATURES, true);
        effect.setText("Other creatures you control gain trample");
        Ability gainedAbility = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new GenericManaCost(2));
        DynamicValue xValue = new SourcePermanentPowerCount();
        effect = new BoostControlledEffect(xValue, xValue, Duration.EndOfTurn, StaticFilters.FILTER_PERMANENT_CREATURE, true, true);
        effect.setText("and get +X/+X until end of turn, where X is this creature's power");
        gainedAbility.addEffect(effect);
        gainedAbility.addCost(new TapSourceCost());
        effect = new GainAbilityAttachedEffect(gainedAbility, AttachmentType.EQUIPMENT);
        effect.setText("and \"{2}, {T}: Other creatures you control gain trample and get +X/+X until end of turn, where X is this creature's power.\"");
        ability.addEffect(effect);
        this.addAbility(ability);

        // Equip {3}
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new GenericManaCost(3), false));
    }

    private DragonThroneOfTarkir(final DragonThroneOfTarkir card) {
        super(card);
    }

    @Override
    public DragonThroneOfTarkir copy() {
        return new DragonThroneOfTarkir(this);
    }
}
