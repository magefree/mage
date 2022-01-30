package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.ReconfigureAbility;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ArmguardFamiliar extends CardImpl {

    public ArmguardFamiliar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.EQUIPMENT);
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Ward {2}
        this.addAbility(new WardAbility(new GenericManaCost(2)));

        // Equipped creature gets +2/+1 and has ward {2}.
        Ability ability = new SimpleStaticAbility(new BoostEquippedEffect(2, 1));
        ability.addEffect(new GainAbilityAttachedEffect(
                new WardAbility(new GenericManaCost(2)), AttachmentType.EQUIPMENT
        ).setText("and has ward {2}"));

        // Reconfigure {4}
        this.addAbility(new ReconfigureAbility("{4}"));
    }

    private ArmguardFamiliar(final ArmguardFamiliar card) {
        super(card);
    }

    @Override
    public ArmguardFamiliar copy() {
        return new ArmguardFamiliar(this);
    }
}
