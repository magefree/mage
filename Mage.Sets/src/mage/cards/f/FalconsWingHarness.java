package mage.cards.f;

import java.util.UUID;
import mage.constants.SubType;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAttachToTarget;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Outcome;

/**
 *
 * @author muz
 */
public final class FalconsWingHarness extends CardImpl {

    public FalconsWingHarness(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{U}");

        this.subtype.add(SubType.EQUIPMENT);

        // When this Equipment enters, attach it to target creature you control.
        this.addAbility(new EntersBattlefieldAttachToTarget());

        // Equipped creature gets +1/+1 and has flying and ward {1}.
        Ability ability = new SimpleStaticAbility(new BoostEquippedEffect(1, 1));
        ability.addEffect(new GainAbilityAttachedEffect(
            FlyingAbility.getInstance(), AttachmentType.EQUIPMENT
        ).setText("and has flying"));
        ability.addEffect(new GainAbilityAttachedEffect(
            new WardAbility(new GenericManaCost(1)), AttachmentType.EQUIPMENT
        ).setText("and has ward {1}"));
        this.addAbility(ability);

        // Equip {2}{U}
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new ManaCostsImpl<>("{2}{U}"), false));
    }

    private FalconsWingHarness(final FalconsWingHarness card) {
        super(card);
    }

    @Override
    public FalconsWingHarness copy() {
        return new FalconsWingHarness(this);
    }
}
