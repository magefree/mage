package mage.cards.h;

import mage.MageInt;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HandOfEnlightenment extends CardImpl {

    public HandOfEnlightenment(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.MONK);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        this.color.setWhite(true);
        this.nightCard = true;

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());
    }

    private HandOfEnlightenment(final HandOfEnlightenment card) {
        super(card);
    }

    @Override
    public HandOfEnlightenment copy() {
        return new HandOfEnlightenment(this);
    }
}
