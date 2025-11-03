package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAttachToTarget;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TwinBlades extends CardImpl {

    public TwinBlades(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{R}");

        this.subtype.add(SubType.EQUIPMENT);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // When this Equipment enters, attach it to target creature you control. That creature gains double strike until end of turn.
        Ability ability = new EntersBattlefieldAttachToTarget();
        ability.addEffect(new GainAbilityTargetEffect(DoubleStrikeAbility.getInstance())
                .setText("That creature gains double strike until end of turn"));
        this.addAbility(ability);

        // Equipped creature gets +1/+1.
        this.addAbility(new SimpleStaticAbility(new BoostEquippedEffect(1, 1)));

        // Equip {2}
        this.addAbility(new EquipAbility(2));
    }

    private TwinBlades(final TwinBlades card) {
        super(card);
    }

    @Override
    public TwinBlades copy() {
        return new TwinBlades(this);
    }
}
