package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.game.permanent.token.TreasureToken;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class LongBodiedGreyDog extends CardImpl {

    public LongBodiedGreyDog(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}");

        this.subtype.add(SubType.DOG);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // When this creature enters, create a tapped Treasure token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
            new CreateTokenEffect(new TreasureToken(), 1, true)
        ));
    }

    private LongBodiedGreyDog(final LongBodiedGreyDog card) {
        super(card);
    }

    @Override
    public LongBodiedGreyDog copy() {
        return new LongBodiedGreyDog(this);
    }
}
