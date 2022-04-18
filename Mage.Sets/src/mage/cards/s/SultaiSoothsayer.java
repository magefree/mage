package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.effects.common.LookLibraryControllerEffect.PutCards;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class SultaiSoothsayer extends CardImpl {

    public SultaiSoothsayer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{G}{U}");
        this.subtype.add(SubType.NAGA);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(2);
        this.toughness = new MageInt(5);

        // When Sultai Soothsayer enters the battlefield, look at the top four cards of your library.
        // Put one of them into your hand and the rest into your graveyard.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new LookLibraryAndPickControllerEffect(4, 1, PutCards.HAND, PutCards.GRAVEYARD)));
    }

    private SultaiSoothsayer(final SultaiSoothsayer card) {
        super(card);
    }

    @Override
    public SultaiSoothsayer copy() {
        return new SultaiSoothsayer(this);
    }
}
