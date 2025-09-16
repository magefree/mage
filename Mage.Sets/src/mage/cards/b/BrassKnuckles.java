package mage.cards.b;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.CastSourceTriggeredAbility;
import mage.abilities.effects.common.CopySourceSpellEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.permanent.AttachedToAttachedPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BrassKnuckles extends CardImpl {
    private static final FilterPermanent filter = new FilterPermanent("Equipment attached to it");

    static {
        filter.add(SubType.EQUIPMENT.getPredicate());
        filter.add(AttachedToAttachedPredicate.instance);
    }

    private static final Condition twoEquipmentAttachedToAttached = new PermanentsOnTheBattlefieldCondition(filter, ComparisonType.OR_GREATER, 2);

    public BrassKnuckles(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        this.subtype.add(SubType.EQUIPMENT);

        // When you cast this spell, copy it.
        this.addAbility(new CastSourceTriggeredAbility(new CopySourceSpellEffect().setText("copy it")));

        // Equipped creature has double strike as long as two or more Equipment are attached to it.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilityAttachedEffect(
                        DoubleStrikeAbility.getInstance(), AttachmentType.EQUIPMENT
                ), twoEquipmentAttachedToAttached, "equipped creature has double strike " +
                "as long as two or more Equipment are attached to it"
        )));

        // Equip {1}
        this.addAbility(new EquipAbility(1));
    }

    private BrassKnuckles(final BrassKnuckles card) {
        super(card);
    }

    @Override
    public BrassKnuckles copy() {
        return new BrassKnuckles(this);
    }
}
