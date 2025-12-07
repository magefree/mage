package mage.cards.s;

import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.common.UnlockThisDoorTriggeredAbility;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardSetInfo;
import mage.cards.RoomCard;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.target.common.TargetAttackingCreature;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 *
 * @author oscscull
 */
public final class SurgicalSuiteHospitalRoom extends RoomCard {
    private static final FilterCard filter = new FilterCreatureCard(
            "creature card with mana value 3 or less from your graveyard");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 4));
    }

    public SurgicalSuiteHospitalRoom(UUID ownerId, CardSetInfo setInfo) {
        // Surgical Suite
        // {1}{W}
        // When you unlock this door, return target creature card with mana value 3 or
        // less from your graveyard to the battlefield.
        // Hospital Room
        // {3}{W}
        // Enchantment -- Room
        // Whenever you attack, put a +1/+1 counter on target attacking creature.
        super(ownerId, setInfo,
                "{1}{W}", "{3}{W}");
        this.subtype.add(SubType.ROOM);

        // Left half ability - "When you unlock this door, return target creature card with mana value 3 or less from your graveyard to the battlefield."
        UnlockThisDoorTriggeredAbility left = new UnlockThisDoorTriggeredAbility(
                new ReturnFromGraveyardToBattlefieldTargetEffect(), false, true);
        left.addTarget(new TargetCardInYourGraveyard(filter));
        this.getLeftHalfCard().addAbility(left);

        // Right half ability - "Whenever you attack, put a +1/+1 counter on target attacking creature."
        AttacksWithCreaturesTriggeredAbility right = new AttacksWithCreaturesTriggeredAbility(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance()), 1
        );
        right.addTarget(new TargetAttackingCreature());
        this.getRightHalfCard().addAbility(right);

    }

    private SurgicalSuiteHospitalRoom(final SurgicalSuiteHospitalRoom card) {
        super(card);
    }

    @Override
    public SurgicalSuiteHospitalRoom copy() {
        return new SurgicalSuiteHospitalRoom(this);
    }
}
