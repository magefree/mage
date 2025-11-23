package mage.cards.t;

import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.ShrinesYouControlCount;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheSpiritOasis extends CardImpl {

    public TheSpiritOasis(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SHRINE);

        // When The Spirit Oasis enters, draw a card for each Shrine you control.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DrawCardSourceControllerEffect(ShrinesYouControlCount.FOR_EACH)));

        // Whenever another Shrine you control enters, draw a card.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(new DrawCardSourceControllerEffect(1), StaticFilters.FILTER_ANOTHER_CONTROLLED_SHRINE));
    }

    private TheSpiritOasis(final TheSpiritOasis card) {
        super(card);
    }

    @Override
    public TheSpiritOasis copy() {
        return new TheSpiritOasis(this);
    }
}
