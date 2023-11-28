package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.CavesControlledAndInGraveCount;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class SinuousBenthisaur extends CardImpl {

    public SinuousBenthisaur(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{U}");

        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // When Sinuous Benthisaur enters the battlefield, look at the top X cards of your library, where X is the number of Caves you control plus the number of Cave cards in your graveyard. Put two of those cards into your hand and the rest on the bottom of your library in a random order.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new LookLibraryAndPickControllerEffect(
                        CavesControlledAndInGraveCount.WHERE_X,
                        2, PutCards.HAND, PutCards.BOTTOM_RANDOM
                )
        ).addHint(CavesControlledAndInGraveCount.getHint()));
    }

    private SinuousBenthisaur(final SinuousBenthisaur card) {
        super(card);
    }

    @Override
    public SinuousBenthisaur copy() {
        return new SinuousBenthisaur(this);
    }
}
