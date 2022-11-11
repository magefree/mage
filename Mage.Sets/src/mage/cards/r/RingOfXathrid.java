package mage.cards.r;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.common.AttachedToMatchesFilterCondition;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.RegenerateAttachedEffect;
import mage.abilities.effects.common.counter.AddPlusOneCountersAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;

/**
 *
 * @author awjackson
 */
public final class RingOfXathrid extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(new ColorPredicate(ObjectColor.BLACK));
    }

    public RingOfXathrid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{2}");
        this.subtype.add(SubType.EQUIPMENT);

        // {2}: Regenerate equipped creature.
        this.addAbility(new SimpleActivatedAbility(new RegenerateAttachedEffect(AttachmentType.EQUIPMENT), new GenericManaCost(2)));

        // At the beginning of your upkeep, put a +1/+1 counter on equipped creature if it's black.
        Effect effect = new ConditionalOneShotEffect(
                new AddPlusOneCountersAttachedEffect(1),
                new AttachedToMatchesFilterCondition(filter),
                "put a +1/+1 counter on equipped creature if it's black"
        );
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(effect, TargetController.YOU, false));

        // Equip {1}
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new GenericManaCost(1)));
    }

    private RingOfXathrid(final RingOfXathrid card) {
        super(card);
    }

    @Override
    public RingOfXathrid copy() {
        return new RingOfXathrid(this);
    }
}
