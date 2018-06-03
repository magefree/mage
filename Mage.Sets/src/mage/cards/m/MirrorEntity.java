
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.VariableManaCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.effects.common.continuous.SetPowerToughnessAllEffect;
import mage.abilities.keyword.ChangelingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.SubLayer;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;

/**
 *
 * @author Plopman
 */
public final class MirrorEntity extends CardImpl {

    static private FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("Creatures you control");

    public MirrorEntity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}");
        this.subtype.add(SubType.SHAPESHIFTER);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Changeling
        this.addAbility(ChangelingAbility.getInstance());
        // {X}: Until end of turn, creatures you control have base power and toughness X/X and gain all creature types.
        DynamicValue variableMana = new ManacostVariableValue();
        Effect effect = new SetPowerToughnessAllEffect(variableMana, variableMana, Duration.EndOfTurn, filter, true);
        effect.setText("Until end of turn, creatures you control have base power and toughness X/X");
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new VariableManaCost());
        effect = new GainAbilityAllEffect(ChangelingAbility.getInstance(), Duration.EndOfTurn, filter, false, Layer.TypeChangingEffects_4, SubLayer.NA);
        effect.setText("and gain all creature types");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    public MirrorEntity(final MirrorEntity card) {
        super(card);
    }

    @Override
    public MirrorEntity copy() {
        return new MirrorEntity(this);
    }
}
