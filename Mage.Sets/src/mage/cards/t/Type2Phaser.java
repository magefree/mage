package mage.cards.t;

import java.util.UUID;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.target.common.TargetOpponentsCreaturePermanent;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class Type2Phaser extends CardImpl {

    public Type2Phaser(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        this.subtype.add(SubType.EQUIPMENT);

        // When this Equipment enters, tap target creature an opponent controls and put a stun counter on it.
        Ability ability = new EntersBattlefieldTriggeredAbility(new TapTargetEffect());
        ability.addEffect(new AddCountersTargetEffect(CounterType.STUN.createInstance()).setText("and put a stun counter on it"));
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        this.addAbility(ability);

        // Equipped creature gets +3/+0.
        this.addAbility(new SimpleStaticAbility(new BoostEquippedEffect(3, 0)));

        // Equip {3}
        this.addAbility(new EquipAbility(3));
    }

    private Type2Phaser(final Type2Phaser card) {
        super(card);
    }

    @Override
    public Type2Phaser copy() {
        return new Type2Phaser(this);
    }
}
