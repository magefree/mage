package mage.cards.r;

import mage.abilities.common.EntersBattlefieldAttachToTarget;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.EquippedHasSubtypeCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RelicAxe extends CardImpl {

    private static final Condition condition = new EquippedHasSubtypeCondition(SubType.WARRIOR);

    public RelicAxe(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        this.subtype.add(SubType.EQUIPMENT);

        // When Relic Axe enters the battlefield, attach it to target creature you control.
        this.addAbility(new EntersBattlefieldAttachToTarget());

        // Equipped creature gets +1/+1. If it's a Warrior, it gets +2/+1 instead.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new BoostEquippedEffect(2, 1), new BoostEquippedEffect(1, 1),
                condition, "equipped creature gets +1/+1. If it's a Warrior, it gets +2/+1 instead"
        )));

        // Equip {2}
        this.addAbility(new EquipAbility(2, false));
    }

    private RelicAxe(final RelicAxe card) {
        super(card);
    }

    @Override
    public RelicAxe copy() {
        return new RelicAxe(this);
    }
}
