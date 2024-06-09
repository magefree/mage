package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.RevealCardsFromLibraryUntilEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class ThicketElemental extends CardImpl {

    public ThicketElemental(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{G}");
        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Kicker {1}{G}
        this.addAbility(new KickerAbility("{1}{G}"));

        // When Thicket Elemental enters the battlefield, if it was kicked, you may reveal cards from the top of your library until you reveal a creature card. If you do, put that card onto the battlefield and shuffle all other cards revealed this way into your library.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(new RevealCardsFromLibraryUntilEffect(
                        StaticFilters.FILTER_CARD_CREATURE, PutCards.BATTLEFIELD, PutCards.SHUFFLE
                )), KickedCondition.ONCE, "When {this} enters the battlefield, if it was kicked, " +
                "you may reveal cards from the top of your library until you reveal a creature card. If you do, " +
                "put that card onto the battlefield and shuffle all other cards revealed this way into your library."
        ));
    }

    private ThicketElemental(final ThicketElemental card) {
        super(card);
    }

    @Override
    public ThicketElemental copy() {
        return new ThicketElemental(this);
    }
}
