package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAttachToTarget;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;

import java.util.UUID;

/**
 *
 * @author ciaccona007
 */
public final class CelestialArmor extends CardImpl {

    public CelestialArmor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{W}");
        
        this.subtype.add(SubType.EQUIPMENT);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // When this Equipment enters, attach it to target creature you control. That creature gains hexproof and indestructible until end of turn.
        Ability entersAbility = new EntersBattlefieldAttachToTarget();
        entersAbility.addEffect(new GainAbilityTargetEffect(HexproofAbility.getInstance())
                .setText("that creature gains hexproof"));
        entersAbility.addEffect(new GainAbilityTargetEffect(IndestructibleAbility.getInstance())
                .setText("and indestructible until end of turn"));
        this.addAbility(entersAbility);

        // Equipped creature gets +2/+0 and has flying.
        Ability staticAbility = new SimpleStaticAbility(new BoostEquippedEffect(2, 0));
        staticAbility.addEffect(new GainAbilityAttachedEffect(
                FlyingAbility.getInstance(), AttachmentType.EQUIPMENT
        ).setText("and has flying"));
        this.addAbility(staticAbility);

        // Equip {3}{W}
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new ManaCostsImpl<>("{3}{W}"), false));
    }

    private CelestialArmor(final CelestialArmor card) {
        super(card);
    }

    @Override
    public CelestialArmor copy() {
        return new CelestialArmor(this);
    }
}
