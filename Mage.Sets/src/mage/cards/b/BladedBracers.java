package mage.cards.b;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.EquippedHasSubtypeCondition;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author noxx
 */
public final class BladedBracers extends CardImpl {

    private static final String ruleText = "As long as equipped creature is a Human or an Angel, it has vigilance";
    private static final Condition condition = new EquippedHasSubtypeCondition(SubType.HUMAN, SubType.ANGEL);

    public BladedBracers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +1/+1.
        this.addAbility(new SimpleStaticAbility(new BoostEquippedEffect(1, 1)));

        // As long as equipped creature is a Human or an Angel, it has vigilance.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilityAttachedEffect(
                        VigilanceAbility.getInstance(), AttachmentType.EQUIPMENT
                ), condition, ruleText
        )));

        // Equip {2}
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new GenericManaCost(2)));
    }

    private BladedBracers(final BladedBracers card) {
        super(card);
    }

    @Override
    public BladedBracers copy() {
        return new BladedBracers(this);
    }
}
