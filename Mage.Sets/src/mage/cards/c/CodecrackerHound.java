package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.keyword.WarpAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CodecrackerHound extends CardImpl {

    public CodecrackerHound(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.DOG);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // When this creature enters, look at the top two cards of your library. Put one into your hand and the other into your graveyard.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new LookLibraryAndPickControllerEffect(2, 1, PutCards.HAND, PutCards.GRAVEYARD)
        ));

        // Warp {2}{U}
        this.addAbility(new WarpAbility(this, "{2}{U}"));
    }

    private CodecrackerHound(final CodecrackerHound card) {
        super(card);
    }

    @Override
    public CodecrackerHound copy() {
        return new CodecrackerHound(this);
    }
}
