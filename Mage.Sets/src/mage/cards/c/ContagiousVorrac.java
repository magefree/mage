package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.effects.common.counter.ProliferateEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ContagiousVorrac extends CardImpl {

    public ContagiousVorrac(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.BOAR);
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Contagious Vorrac enters the battlefield, look at the top four cards of your library. You may reveal a land card from among them and put it into your hand. Put the rest on the bottom of your library in a random order. If you didn't put a card into your hand this way, proliferate.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new LookLibraryAndPickControllerEffect(
                4, 1, StaticFilters.FILTER_CARD_LAND_A, PutCards.HAND, PutCards.BOTTOM_RANDOM
        ).withOtherwiseEffect(new ProliferateEffect())));
    }

    private ContagiousVorrac(final ContagiousVorrac card) {
        super(card);
    }

    @Override
    public ContagiousVorrac copy() {
        return new ContagiousVorrac(this);
    }
}
