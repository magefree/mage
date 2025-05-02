package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterPermanentCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ShepherdOfTheClouds extends CardImpl {

    private static final FilterCard filter
            = new FilterPermanentCard("permanent card with mana value 3 or less from your graveyard");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 4));
    }

    private static final Condition condition
            = new PermanentsOnTheBattlefieldCondition(new FilterControlledPermanent(SubType.MOUNT));
    private static final Hint hint = new ConditionHint(condition, "You control a Mount");

    public ShepherdOfTheClouds(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}");

        this.subtype.add(SubType.PEGASUS);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // When Shepherd of the Clouds enters the battlefield, return target permanent card with mana value 3 or less from your graveyard to your hand. Return that card to the battlefield instead if you control a Mount.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ConditionalOneShotEffect(
                new ReturnFromGraveyardToBattlefieldTargetEffect(), new ReturnFromGraveyardToHandTargetEffect(),
                condition, "return target permanent card with mana value 3 or less from your graveyard " +
                "to your hand. Return that card to the battlefield instead if you control a Mount"
        ));
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability.addHint(hint));
    }

    private ShepherdOfTheClouds(final ShepherdOfTheClouds card) {
        super(card);
    }

    @Override
    public ShepherdOfTheClouds copy() {
        return new ShepherdOfTheClouds(this);
    }
}
