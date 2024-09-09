package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.LifeCompareCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.PreventDamageToSourceEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;

import java.util.UUID;

/**
 * @author JvdB01, xenohedron
 */
public final class CaduceusStaffOfHermes extends CardImpl {

    private static final Condition condition = new LifeCompareCondition(TargetController.YOU, ComparisonType.OR_GREATER, 30);

    public CaduceusStaffOfHermes(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{W}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature has lifelink.
        this.addAbility(new SimpleStaticAbility(new GainAbilityAttachedEffect(LifelinkAbility.getInstance(), AttachmentType.EQUIPMENT)));

        // As long as you have 30 or more life, equipped creature gets +5/+5 and has indestructible and "Prevent all damage that would be dealt to this creature."
        Ability ability = new SimpleStaticAbility(new ConditionalContinuousEffect(
                new BoostEquippedEffect(5, 5), condition,
                "As long as you have 30 or more life, equipped creature gets +5/+5"
        ));
        ability.addEffect(new ConditionalContinuousEffect(
                new GainAbilityAttachedEffect(IndestructibleAbility.getInstance(), AttachmentType.EQUIPMENT), condition,
                "and has indestructible"
        ));
        ability.addEffect(new ConditionalContinuousEffect(
                new GainAbilityAttachedEffect(new SimpleStaticAbility(
                        new PreventDamageToSourceEffect(Duration.WhileOnBattlefield, Integer.MAX_VALUE)
                                .setText("Prevent all damage that would be dealt to this creature")
                ), AttachmentType.EQUIPMENT), condition,
                "and has \"Prevent all damage that would be dealt to this creature.\""
        ));
        this.addAbility(ability);

        // Equip {W}{W}
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new ManaCostsImpl<>("{W}{W}"), false));
    }

    private CaduceusStaffOfHermes(final CaduceusStaffOfHermes card) {
        super(card);
    }

    @Override
    public CaduceusStaffOfHermes copy() {
        return new CaduceusStaffOfHermes(this);
    }
}
