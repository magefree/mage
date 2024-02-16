package mage.cards.t;

import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.AttachedToMatchesFilterCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;

import java.util.UUID;

/**
 * @author LevelX
 */
public final class TenzaGodosMaul extends CardImpl {

    private static final FilterCreaturePermanent legendaryFilter = new FilterCreaturePermanent("legendary");
    private static final FilterCreaturePermanent redFilter = new FilterCreaturePermanent("red");

    static {
        legendaryFilter.add(SuperType.LEGENDARY.getPredicate());
        redFilter.add(new ColorPredicate(ObjectColor.RED));
    }

    private static final Condition condition1 = new AttachedToMatchesFilterCondition(legendaryFilter);
    private static final Condition condition2 = new AttachedToMatchesFilterCondition(redFilter);

    public TenzaGodosMaul(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +1/+1. As long as it's legendary, it gets an additional +2/+2. As long as it's red, it has trample.
        Ability ability = new SimpleStaticAbility(new BoostEquippedEffect(1, 1));
        ability.addEffect(new ConditionalContinuousEffect(
                new BoostEquippedEffect(2, 2), condition1,
                "As long as it's legendary, it gets an additional +2/+2"
        ));
        ability.addEffect(new ConditionalContinuousEffect(
                new GainAbilityAttachedEffect(
                        TrampleAbility.getInstance(), AttachmentType.EQUIPMENT
                ), condition2, "As long as it's red, it has trample."
        ));
        this.addAbility(ability);

        // Equip {1}
        this.addAbility(new EquipAbility(1));
    }

    private TenzaGodosMaul(final TenzaGodosMaul card) {
        super(card);
    }

    @Override
    public TenzaGodosMaul copy() {
        return new TenzaGodosMaul(this);
    }
}
