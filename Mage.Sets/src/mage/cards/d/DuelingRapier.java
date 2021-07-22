package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.FlashAbility;
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
public final class DuelingRapier extends CardImpl {

    public DuelingRapier(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{R}");

        this.subtype.add(SubType.EQUIPMENT);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // When Dueling Rapier enters the battlefield, attach it to target creature you control.
        Ability ability = new EntersBattlefieldTriggeredAbility(new AttachEffect(
                Outcome.BoostCreature, "attach it to target creature you control"
        ), false);
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);

        // Equipped creature gets +2/+0.
        this.addAbility(new SimpleStaticAbility(new BoostEquippedEffect(2, 0)));

        // Equip {4}
        this.addAbility(new EquipAbility(4));
    }

    private DuelingRapier(final DuelingRapier card) {
        super(card);
    }

    @Override
    public DuelingRapier copy() {
        return new DuelingRapier(this);
    }
}
