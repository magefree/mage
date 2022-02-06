package mage.cards.k;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.DealsDamageToACreatureTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.*;
import mage.constants.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.filter.StaticFilters;

/**
 *
 * @author weirddan455
 */
public final class KaldraCompleat extends CardImpl {

    public KaldraCompleat(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{7}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.EQUIPMENT);

        // Living weapon
        this.addAbility(new LivingWeaponAbility());

        // Indestructible
        this.addAbility(IndestructibleAbility.getInstance());

        // Equipped creature gets +5/+5 and has first strike, trample, indestructible, haste, and "Whenever this creature deals combat damage to a creature, exile that creature."
        Ability ability = new SimpleStaticAbility(new BoostEquippedEffect(5, 5));
        ability.addEffect(new GainAbilityAttachedEffect(
                FirstStrikeAbility.getInstance(),
                AttachmentType.EQUIPMENT,
                Duration.WhileOnBattlefield,
                "and has first strike"
        ));
        ability.addEffect(new GainAbilityAttachedEffect(
                TrampleAbility.getInstance(),
                AttachmentType.EQUIPMENT,
                Duration.WhileOnBattlefield,
                ", trample"
        ));
        ability.addEffect(new GainAbilityAttachedEffect(
                IndestructibleAbility.getInstance(),
                AttachmentType.EQUIPMENT,
                Duration.WhileOnBattlefield,
                ", indestructible"
        ));
        ability.addEffect(new GainAbilityAttachedEffect(
                HasteAbility.getInstance(),
                AttachmentType.EQUIPMENT,
                Duration.WhileOnBattlefield,
                ", haste"
        ));
        ability.addEffect(new GainAbilityAttachedEffect(
                new DealsDamageToACreatureTriggeredAbility(
                        // if a creature is dealt lethal damage, it is dies as a state-based action and can't be found to exile
                        new ExileTargetEffect(null, "exile that creature", Zone.BATTLEFIELD).setToSourceExileZone(true),
                        true,
                        false,
                        true,
                        StaticFilters.FILTER_PERMANENT_A_CREATURE
                ),
                AttachmentType.EQUIPMENT,
                Duration.WhileOnBattlefield,
                ", and \"Whenever this creature deals combat damage to a creature, exile that creature.\""
        ));
        this.addAbility(ability);

        // Equip {7}
        this.addAbility(new EquipAbility(7, false));
    }

    private KaldraCompleat(final KaldraCompleat card) {
        super(card);
    }

    @Override
    public KaldraCompleat copy() {
        return new KaldraCompleat(this);
    }
}
