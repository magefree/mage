package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MeteorSword extends CardImpl {

    public MeteorSword(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{7}");

        this.subtype.add(SubType.EQUIPMENT);

        // When this Equipment enters, destroy target permanent.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DestroyTargetEffect());
        ability.addTarget(new TargetPermanent());
        this.addAbility(ability);

        // Equipped creature gets +3/+3.
        this.addAbility(new SimpleStaticAbility(new BoostEquippedEffect(3, 3)));

        // Equip {3}
        this.addAbility(new EquipAbility(3));
    }

    private MeteorSword(final MeteorSword card) {
        super(card);
    }

    @Override
    public MeteorSword copy() {
        return new MeteorSword(this);
    }
}
