package mage.cards.s;

import java.util.UUID;

import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.abilities.keyword.LivingMetalAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.delayed.AtTheEndOfCombatDelayedTriggeredAbility;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 * @author grimreap124
 */
public final class SlicerHighSpeedAntagonist extends CardImpl {

    public SlicerHighSpeedAntagonist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[] { CardType.ARTIFACT }, "");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);
        this.color.setRed(true);
        this.nightCard = true;

        // Living metal
        this.addAbility(new LivingMetalAbility());

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Whenever Slicer deals combat damage to a player, convert it at end of combat.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(
                new CreateDelayedTriggeredAbilityEffect(
                        new AtTheEndOfCombatDelayedTriggeredAbility(new TransformSourceEffect()))
                        .setText("convert it at end of combat"),
                false));

    }

    private SlicerHighSpeedAntagonist(final SlicerHighSpeedAntagonist card) {
        super(card);
    }

    @Override
    public SlicerHighSpeedAntagonist copy() {
        return new SlicerHighSpeedAntagonist(this);
    }
}
