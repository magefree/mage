

package mage.cards.s;

import java.util.UUID;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.EquippedHasSubtypeCondition;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;

/**
 *
 * @author fireshoes
 */
public final class ScroungedScythe extends CardImpl {

    public ScroungedScythe(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"");
        this.subtype.add(SubType.EQUIPMENT);

        this.nightCard = true;

        // Equipped creature gets +1/+1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEquippedEffect(1, 1)));

        // As long as equipped creature is a Human, it has menace.
        this.addAbility(new SimpleStaticAbility(
                Zone.BATTLEFIELD,
                new ConditionalContinuousEffect(
                        new GainAbilityAttachedEffect(new MenaceAbility(), AttachmentType.EQUIPMENT),
                        new EquippedHasSubtypeCondition(SubType.HUMAN),
                        "As long as equipped creature is a Human, it has menace. " +
                                "<i>(It can't be blocked except by two or more creatures.)</i>")));

        // Equip {2}
        this.addAbility(new EquipAbility(2, false));
    }

    private ScroungedScythe(final ScroungedScythe card) {
        super(card);
    }

    @Override
    public ScroungedScythe copy() {
        return new ScroungedScythe(this);
    }
}