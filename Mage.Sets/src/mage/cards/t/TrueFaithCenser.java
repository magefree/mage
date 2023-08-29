package mage.cards.t;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.EquippedHasSubtypeCondition;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;

/**
 *
 * @author fireshoes
 */
public final class TrueFaithCenser extends CardImpl {

    private static final String staticText = "As long as equipped creature is a Human, it gets an additional +1/+0";

    public TrueFaithCenser(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{2}");
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +1/+1 and has vigilance.
        Effect effect = new BoostEquippedEffect(1, 1);
        effect.setText("Equipped creature gets +1/+1");
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, effect);
        effect = new GainAbilityAttachedEffect(VigilanceAbility.getInstance(), AttachmentType.EQUIPMENT);
        effect.setText("and has vigilance");
        ability.addEffect(effect);
        this.addAbility(ability);

        // As long as equipped creature is a Human, it gets an additional +1/+0.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new ConditionalContinuousEffect(new BoostEquippedEffect(1, 0),
                new EquippedHasSubtypeCondition(SubType.HUMAN), staticText)));

        // Equip {2}
        this.addAbility(new EquipAbility(Outcome.AddAbility, new GenericManaCost(2)));
    }

    private TrueFaithCenser(final TrueFaithCenser card) {
        super(card);
    }

    @Override
    public TrueFaithCenser copy() {
        return new TrueFaithCenser(this);
    }
}
