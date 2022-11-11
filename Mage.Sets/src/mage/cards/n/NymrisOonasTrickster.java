package mage.cards.n;

import mage.MageInt;
import mage.abilities.common.FirstSpellOpponentsTurnTriggeredAbility;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.constants.SubType;
import mage.constants.SuperType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NymrisOonasTrickster extends CardImpl {

    public NymrisOonasTrickster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{B}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.FAERIE);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(6);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever you cast your first spell during each opponent's turn, look at the top two cards of your library.
        // Put one of those cards into your hand and the other into your graveyard.
        this.addAbility(new FirstSpellOpponentsTurnTriggeredAbility(
                new LookLibraryAndPickControllerEffect(2, 1, PutCards.HAND, PutCards.GRAVEYARD),
                false));
    }

    private NymrisOonasTrickster(final NymrisOonasTrickster card) {
        super(card);
    }

    @Override
    public NymrisOonasTrickster copy() {
        return new NymrisOonasTrickster(this);
    }
}
