package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.effects.common.LookLibraryControllerEffect;
import mage.constants.SubType;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;

/**
 *
 * @author weirddan455
 */
public final class FloriferousVinewall extends CardImpl {

    public FloriferousVinewall(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.PLANT);
        this.subtype.add(SubType.WALL);
        this.power = new MageInt(0);
        this.toughness = new MageInt(2);

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // When Floriferous Vinewall enters the battlefield, look at the top six cards of your library. You may reveal a land card from among them and put it into your hand. Put the rest on the bottom of your library in a random order.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new LookLibraryAndPickControllerEffect(6, 1, StaticFilters.FILTER_CARD_LAND,
                        LookLibraryControllerEffect.PutCards.HAND, LookLibraryControllerEffect.PutCards.BOTTOM_RANDOM)
        ));
    }

    private FloriferousVinewall(final FloriferousVinewall card) {
        super(card);
    }

    @Override
    public FloriferousVinewall copy() {
        return new FloriferousVinewall(this);
    }
}
