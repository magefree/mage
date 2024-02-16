package mage.cards.h;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HuntersBlowgun extends CardImpl {

    public HuntersBlowgun(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");

        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +1/+1.
        this.addAbility(new SimpleStaticAbility(new BoostEquippedEffect(1, 1)));

        // Equipped creature has deathtouch as long as it's your turn. Otherwise, it has reach.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilityAttachedEffect(DeathtouchAbility.getInstance(), AttachmentType.EQUIPMENT),
                new GainAbilityAttachedEffect(ReachAbility.getInstance(), AttachmentType.EQUIPMENT),
                MyTurnCondition.instance, "equipped creature has deathtouch as long as it's your turn. Otherwise, it has reach"
        )));

        // Equip {2}
        this.addAbility(new EquipAbility(2));
    }

    private HuntersBlowgun(final HuntersBlowgun card) {
        super(card);
    }

    @Override
    public HuntersBlowgun copy() {
        return new HuntersBlowgun(this);
    }
}
