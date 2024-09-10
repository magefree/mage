package mage.cards.f;

import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.DrawCardAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FriendlyTeddy extends CardImpl {

    public FriendlyTeddy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}");

        this.subtype.add(SubType.BEAR);
        this.subtype.add(SubType.TOY);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Friendly Teddy dies, each player draws a card.
        this.addAbility(new DiesSourceTriggeredAbility(new DrawCardAllEffect(1)));
    }

    private FriendlyTeddy(final FriendlyTeddy card) {
        super(card);
    }

    @Override
    public FriendlyTeddy copy() {
        return new FriendlyTeddy(this);
    }
}
