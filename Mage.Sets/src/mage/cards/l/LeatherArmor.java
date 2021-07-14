package mage.cards.l;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.WardAbility;
import mage.constants.AttachmentType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author weirddan455
 */
public final class LeatherArmor extends CardImpl {

    public LeatherArmor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");

        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +0/+1 and has ward {1}.
        Ability ability = new SimpleStaticAbility(new BoostEquippedEffect(0, 1));
        ability.addEffect(new GainAbilityAttachedEffect(
                new WardAbility(new GenericManaCost(1)),
                AttachmentType.EQUIPMENT,
                Duration.WhileOnBattlefield,
                "and has ward {1}"
        ));
        this.addAbility(ability);

        // Equip {0}. Activate only once each turn.
        EquipAbility equipAbility = new EquipAbility(0);
        equipAbility.setMaxActivationsPerTurn(1);
        this.addAbility(equipAbility);
    }

    private LeatherArmor(final LeatherArmor card) {
        super(card);
    }

    @Override
    public LeatherArmor copy() {
        return new LeatherArmor(this);
    }
}
