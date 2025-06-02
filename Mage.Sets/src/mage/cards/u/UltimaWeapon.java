package mage.cards.u;

import mage.abilities.Ability;
import mage.abilities.common.AttacksAttachedTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class UltimaWeapon extends CardImpl {

    public UltimaWeapon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{7}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.EQUIPMENT);

        // Whenever equipped creature attacks, destroy target creature an opponent controls.
        Ability ability = new AttacksAttachedTriggeredAbility(new DestroyTargetEffect());
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        this.addAbility(ability);

        // Equipped creature gets +7/+7.
        this.addAbility(new SimpleStaticAbility(new BoostEquippedEffect(7, 7)));

        // Equip {7}
        this.addAbility(new EquipAbility(7));
    }

    private UltimaWeapon(final UltimaWeapon card) {
        super(card);
    }

    @Override
    public UltimaWeapon copy() {
        return new UltimaWeapon(this);
    }
}
