package mage.cards.l;

import mage.ObjectColor;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.GainLifeTargetEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author Merlingilb
 */
public class LukesLightsaber extends CardImpl {
    public LukesLightsaber(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");
        this.supertype.add(SuperType.LEGENDARY);
        this.addSubType(SubType.EQUIPMENT);

        //Equipped creature gets +2/+0 and has first strike and protection from black.
        this.addAbility(new SimpleStaticAbility(new BoostEquippedEffect(2, 0)));
        this.addAbility(new SimpleStaticAbility(new GainAbilityAttachedEffect(
                FirstStrikeAbility.getInstance(), AttachmentType.EQUIPMENT
        ).setText("and has first strike")));
        this.addAbility(new SimpleStaticAbility(new GainAbilityAttachedEffect(
                ProtectionAbility.from(ObjectColor.BLACK), AttachmentType.EQUIPMENT
        ).setText("and protection from black")));

        //Whenever equipped creature deals combat damage to a player, target player gains 2 life.
        DealsCombatDamageToAPlayerTriggeredAbility dealsCombatDamageToAPlayerTriggeredAbility =
                new DealsCombatDamageToAPlayerTriggeredAbility(new GainLifeTargetEffect(2), false);
        dealsCombatDamageToAPlayerTriggeredAbility.addTarget(new TargetPlayer());
        this.addAbility(new SimpleStaticAbility(new GainAbilityAttachedEffect(
                dealsCombatDamageToAPlayerTriggeredAbility, AttachmentType.EQUIPMENT)));

        //Equip {2}
        this.addAbility(new EquipAbility(Outcome.AddAbility, new GenericManaCost(2), false));
    }

    public LukesLightsaber(final LukesLightsaber card) {
        super(card);
    }

    @Override
    public LukesLightsaber copy() {
        return new LukesLightsaber(this);
    }
}
