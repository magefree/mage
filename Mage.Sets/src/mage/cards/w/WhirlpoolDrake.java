
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.ShuffleHandIntoLibraryDrawThatManySourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class WhirlpoolDrake extends CardImpl {

    public WhirlpoolDrake(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{U}");
        this.subtype.add(SubType.DRAKE);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // When Whirlpool Drake enters the battlefield, shuffle the cards from your hand into your library, then draw that many cards.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ShuffleHandIntoLibraryDrawThatManySourceEffect(), false));

        // When Whirlpool Drake dies, shuffle the cards from your hand into your library, then draw that many cards.
        this.addAbility(new DiesSourceTriggeredAbility(new ShuffleHandIntoLibraryDrawThatManySourceEffect(), false));
    }

    private WhirlpoolDrake(final WhirlpoolDrake card) {
        super(card);
    }

    @Override
    public WhirlpoolDrake copy() {
        return new WhirlpoolDrake(this);
    }
}
