package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BecomesTappedSourceTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.CastFromEverywhereSourceCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.ExileTopXMayPlayUntilEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterPermanentCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class AnnieFlashTheVeteran extends CardImpl {

    private static final FilterPermanentCard filter =
            new FilterPermanentCard("permanent card with mana value 3 or less from your graveyard");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.OR_LESS, 3));
    }

    public AnnieFlashTheVeteran(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{G}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // When Annie Flash, the Veteran enters the battlefield, if you cast it, return target permanent card with mana value 3 or less from your graveyard to the battlefield tapped.
        Ability ability = new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(new ReturnFromGraveyardToBattlefieldTargetEffect(true)),
                CastFromEverywhereSourceCondition.instance,
                "When {this} enters the battlefield, if you cast it, "
                        + "return target permanent card with mana value 3 or less from your graveyard to the battlefield tapped"
        );
        ability.addTarget(new TargetCardInYourGraveyard(filter));

        // Whenever Annie Flash becomes tapped, exile the top two cards of your library. You may play those cards this turn.
        this.addAbility(new BecomesTappedSourceTriggeredAbility(new ExileTopXMayPlayUntilEffect(2, Duration.EndOfTurn)));
    }

    private AnnieFlashTheVeteran(final AnnieFlashTheVeteran card) {
        super(card);
    }

    @Override
    public AnnieFlashTheVeteran copy() {
        return new AnnieFlashTheVeteran(this);
    }
}
