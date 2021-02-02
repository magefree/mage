
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.ShuffleHandIntoLibraryDrawThatManySourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class WhirlpoolRider extends CardImpl {

    public WhirlpoolRider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}");
        this.subtype.add(SubType.MERFOLK);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When Whirlpool Rider enters the battlefield, shuffle the cards from your hand into your library, then draw that many cards.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ShuffleHandIntoLibraryDrawThatManySourceEffect()));

    }

    private WhirlpoolRider(final WhirlpoolRider card) {
        super(card);
    }

    @Override
    public WhirlpoolRider copy() {
        return new WhirlpoolRider(this);
    }
}
