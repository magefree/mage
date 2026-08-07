package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.LandfallAbility;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.PutCards;

/**
 *
 * @author muz
 */
public final class BoughsideWanderers extends CardImpl {

    public BoughsideWanderers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}{G}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // When this creature enters, look at the top four cards of your library. You may reveal a permanent card from among them and put it into your hand. Put the rest on the bottom of your library in a random order.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new LookLibraryAndPickControllerEffect(
            4, 1, StaticFilters.FILTER_CARD_A_PERMANENT,
            PutCards.HAND, PutCards.BOTTOM_RANDOM
        )));

        // Landfall -- Whenever a land you control enters, this creature gets +2/+2 until end of turn.
        this.addAbility(new LandfallAbility(new BoostSourceEffect(2, 2, Duration.EndOfTurn)));
    }

    private BoughsideWanderers(final BoughsideWanderers card) {
        super(card);
    }

    @Override
    public BoughsideWanderers copy() {
        return new BoughsideWanderers(this);
    }
}
