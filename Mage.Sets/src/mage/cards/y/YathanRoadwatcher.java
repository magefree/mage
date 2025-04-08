package mage.cards.y;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.condition.common.CastFromEverywhereSourceCondition;
import mage.abilities.costs.common.MillCardsCost;
import mage.abilities.effects.common.DoWhenCostPaid;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class YathanRoadwatcher extends CardImpl {

    private static final FilterCard filter
            = new FilterCreatureCard("creature card with mana value 3 or less from your graveyard");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 4));
    }

    public YathanRoadwatcher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{B}{G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When this creature enters, if you cast it, mill four cards. When you do, return target creature card with mana value 3 or less from your graveyard to the battlefield.
        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(
                new ReturnFromGraveyardToBattlefieldTargetEffect(), false
        );
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DoWhenCostPaid(
                ability, new MillCardsCost(4), "", false
        )).withInterveningIf(CastFromEverywhereSourceCondition.instance));
    }

    private YathanRoadwatcher(final YathanRoadwatcher card) {
        super(card);
    }

    @Override
    public YathanRoadwatcher copy() {
        return new YathanRoadwatcher(this);
    }
}
