package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.EquippedHasSubtypeCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
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
public final class RelicAxe extends CardImpl {

    private static final Condition condition = new EquippedHasSubtypeCondition(SubType.WARRIOR);

    public RelicAxe(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        this.subtype.add(SubType.EQUIPMENT);

        // When Relic Axe enters the battlefield, attach it to target creature you control.
        Ability ability = new EntersBattlefieldTriggeredAbility(new AttachEffect(
                Outcome.BoostCreature, "attach it to target creature you control"
        ), false);
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);

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
