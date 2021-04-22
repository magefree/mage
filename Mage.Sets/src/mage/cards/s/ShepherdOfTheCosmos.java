package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.ForetellAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.common.FilterPermanentCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ShepherdOfTheCosmos extends CardImpl {

    private static final FilterCard filter = new FilterPermanentCard(
            "permanent card with mana value 2 or less from your graveyard"
    );

    static {
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 3));
    }

    public ShepherdOfTheCosmos(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}{W}");

        this.subtype.add(SubType.ANGEL);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Shepherd of the Cosmos enters the battlefield, return target permanent card with converted mana cost 2 or less from your graveyard to the battlefield.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ReturnFromGraveyardToBattlefieldTargetEffect());
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability);

        // Foretell {3}{W}
        this.addAbility(new ForetellAbility(this, "{3}{W}"));
    }

    private ShepherdOfTheCosmos(final ShepherdOfTheCosmos card) {
        super(card);
    }

    @Override
    public ShepherdOfTheCosmos copy() {
        return new ShepherdOfTheCosmos(this);
    }
}
