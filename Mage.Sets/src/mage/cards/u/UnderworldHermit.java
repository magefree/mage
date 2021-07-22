package mage.cards.u;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.DevotionCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.SquirrelToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class UnderworldHermit extends CardImpl {

    public UnderworldHermit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.PEASANT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Underworld Hermit enters the battlefield, create a number of 1/1 green Squirrel creature tokens equal to your devotion to black.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new CreateTokenEffect(new SquirrelToken(), DevotionCount.B)
                        .setText("create a number of 1/1 green Squirrel creature tokens equal to your devotion to black")
        ).addHint(DevotionCount.B.getHint()));
    }

    private UnderworldHermit(final UnderworldHermit card) {
        super(card);
    }

    @Override
    public UnderworldHermit copy() {
        return new UnderworldHermit(this);
    }
}
