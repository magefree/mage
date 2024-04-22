
package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.common.UnattachCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerCount;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.effects.common.continuous.GainAbilityWithAttachmentEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.target.common.TargetPlayerOrPlaneswalker;

import java.util.UUID;

/**
 * @author AlumiuN
 */
public final class SurestrikeTrident extends CardImpl {

    private static final DynamicValue xValue = new SourcePermanentPowerCount();

    public SurestrikeTrident(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature has first strike and "{T}, Unattach Surestrike Trident: This creature deals damage equal to its power to target player."
        Ability ability = new SimpleStaticAbility(new GainAbilityAttachedEffect(
                FirstStrikeAbility.getInstance(), AttachmentType.EQUIPMENT
        ));
        ability.addEffect(new GainAbilityWithAttachmentEffect(
                "and \"{T}, Unattach {this}: This creature deals damage equal to its power to target player or planeswalker.\"",
                new DamageTargetEffect(xValue)
                        .setText("This creature deals damage equal to its power to target player or planeswalker"),
                new TargetPlayerOrPlaneswalker(), new UnattachCost(), new TapSourceCost()
        ));
        this.addAbility(ability);

        // Equip {4}
        this.addAbility(new EquipAbility(Outcome.Benefit, new GenericManaCost(4)));
    }

    private SurestrikeTrident(final SurestrikeTrident card) {
        super(card);
    }

    @Override
    public SurestrikeTrident copy() {
        return new SurestrikeTrident(this);
    }
}
