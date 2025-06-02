package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.combat.MustBeBlockedByAtLeastOneTargetEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MagitekScythe extends CardImpl {

    public MagitekScythe(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        this.subtype.add(SubType.EQUIPMENT);

        // A Test of Your Reflexes! -- When this Equipment enters, you may attach it to target creature you control. If you do, that creature gains first strike until end of turn and must be blocked this turn if able.
        Ability ability = new EntersBattlefieldTriggeredAbility(new AttachEffect(
                Outcome.BoostCreature, "attach it to target creature you control"
        ), true);
        ability.addTarget(new TargetControlledCreaturePermanent());
        ability.addEffect(new GainAbilityTargetEffect(FirstStrikeAbility.getInstance())
                .setText("If you do, that creature gains first strike until end of turn"));
        ability.addEffect(new MustBeBlockedByAtLeastOneTargetEffect()
                .setText("and must be blocked this turn if able"));
        this.addAbility(ability.withFlavorWord("A Test of Your Reflexes!"));

        // Equipped creature gets +2/+1.
        this.addAbility(new SimpleStaticAbility(new BoostEquippedEffect(2, 1)));

        // Equip {2}
        this.addAbility(new EquipAbility(2));
    }

    private MagitekScythe(final MagitekScythe card) {
        super(card);
    }

    @Override
    public MagitekScythe copy() {
        return new MagitekScythe(this);
    }
}
