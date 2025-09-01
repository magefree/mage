package mage.cards.b;

import mage.MageInt;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.abilities.keyword.ReachAbility;
import mage.abilities.mana.AnyColorManaAbility;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BugenhagenWiseElder extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("you control a creature with power 7 or greater");

    static {
        filter.add(new PowerPredicate(ComparisonType.MORE_THAN, 7));
    }

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(filter);
    private static final Hint hint = new ConditionHint(condition, "You control a creature with power 7 or greater");

    public BugenhagenWiseElder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // At the beginning of your upkeep, if you control a creature with power 7 or greater, draw a card.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new DrawCardSourceControllerEffect(1)).withInterveningIf(condition).addHint(hint));

        // {T}: Add one mana of any color.
        this.addAbility(new AnyColorManaAbility());
    }

    private BugenhagenWiseElder(final BugenhagenWiseElder card) {
        super(card);
    }

    @Override
    public BugenhagenWiseElder copy() {
        return new BugenhagenWiseElder(this);
    }
}
