package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.CastFromEverywhereSourceCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.common.FilterPermanentCard;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HeraldOfTheForgotten extends CardImpl {

    private static final FilterCard filter
            = new FilterPermanentCard("permanent cards with cycling abilities from your graveyard");

    static {
        filter.add(new AbilityPredicate(CyclingAbility.class));
    }

    public HeraldOfTheForgotten(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{6}{W}{W}");

        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Herald of the Forgotten enters the battlefield, if you cast it, return any number of target permanent cards with cycling abilities from your graveyard to the battlefield.
        Ability ability = new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(new ReturnFromGraveyardToBattlefieldTargetEffect()),
                CastFromEverywhereSourceCondition.instance, "When {this} enters the battlefield, if you cast it, " +
                "return any number of target permanent cards with cycling abilities from your graveyard to the battlefield."
        );
        ability.addTarget(new TargetCardInYourGraveyard(0, Integer.MAX_VALUE, filter));
        this.addAbility(ability);
    }

    private HeraldOfTheForgotten(final HeraldOfTheForgotten card) {
        super(card);
    }

    @Override
    public HeraldOfTheForgotten copy() {
        return new HeraldOfTheForgotten(this);
    }
}
