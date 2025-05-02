package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.ExileTargetCardCopyAndCastEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.common.FilterNonlandCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ShikoParagonOfTheWay extends CardImpl {

    private static final FilterCard filter = new FilterNonlandCard("nonland card with mana value 3 or less");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 4));
    }

    public ShikoParagonOfTheWay(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{R}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPIRIT);
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // When Shiko enters, exile target nonland card with mana value 3 or less from your graveyard. Copy it, then you may cast the copy without paying its mana cost.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ExileTargetCardCopyAndCastEffect(true)
                .setText("exile target nonland card with mana value 3 or less from your graveyard. " +
                        "Copy it, then you may cast the copy without paying its mana cost"));
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability);
    }

    private ShikoParagonOfTheWay(final ShikoParagonOfTheWay card) {
        super(card);
    }

    @Override
    public ShikoParagonOfTheWay copy() {
        return new ShikoParagonOfTheWay(this);
    }
}
