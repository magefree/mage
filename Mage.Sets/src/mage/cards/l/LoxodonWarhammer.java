
package mage.cards.l;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.continuous.BoostGainAbilityGenericEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.target.targetpointer.SourceAttachedTargetPointer;

import java.util.UUID;

/**
 *
 * @author anonymous
 */
public final class LoxodonWarhammer extends CardImpl {

    public LoxodonWarhammer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{3}");
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +3/+0 and has trample and lifelink. (If the creature would assign enough damage to its blockers to destroy them, you may have it assign the rest of its damage to defending player or planeswalker. Damage dealt by the creature also causes its controller to gain that much life.)
        this.addAbility(new SimpleStaticAbility(new BoostGainAbilityGenericEffect(
                3, 0, Duration.WhileOnBattlefield,
                TrampleAbility.getInstance(), LifelinkAbility.getInstance()
        ).setTargetPointer(new SourceAttachedTargetPointer("equipped creature"))));

        // Equip (: Attach to target creature you control. Equip only as a sorcery.)
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new GenericManaCost(3), false));
    }

    private LoxodonWarhammer(final LoxodonWarhammer card) {
        super(card);
    }

    @Override
    public LoxodonWarhammer copy() {
        return new LoxodonWarhammer(this);
    }
}
