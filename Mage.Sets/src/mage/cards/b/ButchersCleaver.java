
package mage.cards.b;

import java.util.UUID;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.EquippedHasSubtypeCondition;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;

/**
 *
 * @author nantuko
 */
public final class ButchersCleaver extends CardImpl {

    private static final String staticText = "As long as equipped creature is a Human, it has lifelink";

    public ButchersCleaver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{3}");
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +3/+0.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEquippedEffect(3, 0)));

        // As long as equipped creature is a Human, it has lifelink.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new ConditionalContinuousEffect(new GainAbilityAttachedEffect(LifelinkAbility.getInstance(), AttachmentType.EQUIPMENT),
                new EquippedHasSubtypeCondition(SubType.HUMAN), staticText)));

        // Equip {3}
        this.addAbility(new EquipAbility(Outcome.AddAbility, new GenericManaCost(3), false));

    }

    private ButchersCleaver(final ButchersCleaver card) {
        super(card);
    }

    @Override
    public ButchersCleaver copy() {
        return new ButchersCleaver(this);
    }
}
