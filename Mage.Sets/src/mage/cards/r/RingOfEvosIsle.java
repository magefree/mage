
package mage.cards.r;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.common.AttachedToMatchesFilterCondition;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.effects.common.counter.AddPlusOneCountersAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;

/**
 *
 * @author jeffwadsworth
 */
public final class RingOfEvosIsle extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(new ColorPredicate(ObjectColor.BLUE));
    }

    public RingOfEvosIsle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{2}");
        this.subtype.add(SubType.EQUIPMENT);

        // {2}: Equipped creature gains hexproof until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainAbilityAttachedEffect(HexproofAbility.getInstance(), AttachmentType.EQUIPMENT, Duration.EndOfTurn), new GenericManaCost(2)));

        // At the beginning of your upkeep, put a +1/+1 counter on equipped creature if it's blue.
        TriggeredAbility triggeredAbility = new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new AddPlusOneCountersAttachedEffect(1), TargetController.YOU, false);
        ConditionalInterveningIfTriggeredAbility ability2 = new ConditionalInterveningIfTriggeredAbility(triggeredAbility, new AttachedToMatchesFilterCondition(filter), "At the beginning of your upkeep, put a +1/+1 counter on equipped creature if it's blue.");
        this.addAbility(ability2);

        // Equip {1}
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new GenericManaCost(1)));
    }

    private RingOfEvosIsle(final RingOfEvosIsle card) {
        super(card);
    }

    @Override
    public RingOfEvosIsle copy() {
        return new RingOfEvosIsle(this);
    }
}
