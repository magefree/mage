package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.common.DrawNthCardTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
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
public final class MantleOfTides extends CardImpl {

    public MantleOfTides(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{U}");

        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +1/+2.
        this.addAbility(new SimpleStaticAbility(new BoostEquippedEffect(1, 2)));

        // Whenever you draw your second card each turn, attach Mantle of Tides to target creature you control.
        Ability ability = new DrawNthCardTriggeredAbility(new AttachEffect(
                Outcome.Benefit, "attach {this} to target creature you control"
        ), false, 2);
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);

        // Equip {3}
        this.addAbility(new EquipAbility(3));
    }

    private MantleOfTides(final MantleOfTides card) {
        super(card);
    }

    @Override
    public MantleOfTides copy() {
        return new MantleOfTides(this);
    }
}
