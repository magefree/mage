
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.common.UnattachCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.target.common.TargetPlayerOrPlaneswalker;

/**
 *
 * @author AlumiuN
 */
public final class SurestrikeTrident extends CardImpl {

    public SurestrikeTrident(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature has first strike and "{T}, Unattach Surestrike Trident: This creature deals damage equal to its power to target player."
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAttachedEffect(FirstStrikeAbility.getInstance(), AttachmentType.EQUIPMENT));
        DynamicValue xValue = new SourcePermanentPowerCount();
        Effect effect = new DamageTargetEffect(xValue);
        effect.setText("This creature deals damage equal to its power to target player or planeswalker");
        Ability gainedAbility = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new TapSourceCost());
        gainedAbility.addTarget(new TargetPlayerOrPlaneswalker());
        gainedAbility.addCost(new UnattachCost(this.getName(), this.getId()));
        effect = new GainAbilityAttachedEffect(gainedAbility, AttachmentType.EQUIPMENT);
        effect.setText("and \"{T}, Unattach {this}: This creature deals damage equal to its power to target player.\"");
        ability.addEffect(effect);
        this.addAbility(ability);

        // Equip {4}
        this.addAbility(new EquipAbility(Outcome.Benefit, new GenericManaCost(4)));
    }

    public SurestrikeTrident(final SurestrikeTrident card) {
        super(card);
    }

    @Override
    public SurestrikeTrident copy() {
        return new SurestrikeTrident(this);
    }
}
