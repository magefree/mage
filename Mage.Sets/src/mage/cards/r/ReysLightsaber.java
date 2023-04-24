package mage.cards.r;

import mage.ObjectColor;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.ExileTopXMayPlayUntilEndOfTurnEffect;
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
public class ReysLightsaber extends CardImpl {
    public ReysLightsaber(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");
        this.addSuperType(SuperType.LEGENDARY);
        this.addSubType(SubType.EQUIPMENT);

        //Equipped creature gets +2/+0 and has first strike and protection from red.
        this.addAbility(new SimpleStaticAbility(new BoostEquippedEffect(2, 0)));
        this.addAbility(new SimpleStaticAbility(new GainAbilityAttachedEffect(
                FirstStrikeAbility.getInstance(), AttachmentType.EQUIPMENT).setText("and has first strike")));
        this.addAbility(new SimpleStaticAbility(new GainAbilityAttachedEffect(
                ProtectionAbility.from(ObjectColor.RED), AttachmentType.EQUIPMENT).setText("and protection from red")));

        //Whenever equipped creature deals combat damage to a player, target player exiles the top card of their library.
        //That player may play that card until next turn.
        DealsCombatDamageToAPlayerTriggeredAbility dealsCombatDamageToAPlayerTriggeredAbility =
                new DealsCombatDamageToAPlayerTriggeredAbility(
                        new ExileTopXMayPlayUntilEndOfTurnEffect(1, false), false);
        dealsCombatDamageToAPlayerTriggeredAbility.addTarget(new TargetPlayer());
        this.addAbility(new SimpleStaticAbility(new GainAbilityAttachedEffect(
                dealsCombatDamageToAPlayerTriggeredAbility, AttachmentType.EQUIPMENT)));

        //Equip {2}
        this.addAbility(new EquipAbility(2, false));
    }

    public ReysLightsaber(final ReysLightsaber card) {
        super(card);
    }

    @Override
    public ReysLightsaber copy() {
        return new ReysLightsaber(this);
    }
}
