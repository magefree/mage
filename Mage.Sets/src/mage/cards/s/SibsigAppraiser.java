package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SibsigAppraiser extends CardImpl {

    public SibsigAppraiser(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.ADVISOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // When this creature enters, look at the top two cards of your library. Put one of them into your hand and the other into your graveyard.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new LookLibraryAndPickControllerEffect(
                2, 1, PutCards.HAND, PutCards.GRAVEYARD
        )));
    }

    private SibsigAppraiser(final SibsigAppraiser card) {
        super(card);
    }

    @Override
    public SibsigAppraiser copy() {
        return new SibsigAppraiser(this);
    }
}
