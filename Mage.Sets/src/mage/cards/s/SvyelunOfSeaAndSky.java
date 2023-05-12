package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SvyelunOfSeaAndSky extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.MERFOLK, "Merfolk");

    static {
        filter.add(AnotherPredicate.instance);
    }

    private static final Condition condition
            = new PermanentsOnTheBattlefieldCondition(filter, ComparisonType.MORE_THAN, 1);
    private static final Hint hint
            = new ValueHint("Other Merfolk you control", new PermanentsOnBattlefieldCount(filter));

    public SvyelunOfSeaAndSky(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.GOD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Svyelun of Sea and Sky has indestructible as long as you control at least two other Merfolk.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(IndestructibleAbility.getInstance(), Duration.WhileOnBattlefield),
                condition, "{this} has indestructible as long as you control at least two other Merfolk"
        )));

        // Whenever Svyelun attacks, draw a card.
        this.addAbility(new AttacksTriggeredAbility(new DrawCardSourceControllerEffect(1), false));

        // Other Merfolk you control have ward {1}.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                new WardAbility(new GenericManaCost(1)), Duration.WhileOnBattlefield, filter, true
        )));
    }

    private SvyelunOfSeaAndSky(final SvyelunOfSeaAndSky card) {
        super(card);
    }

    @Override
    public SvyelunOfSeaAndSky copy() {
        return new SvyelunOfSeaAndSky(this);
    }
}
