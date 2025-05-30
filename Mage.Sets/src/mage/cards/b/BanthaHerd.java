
package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.BecomesMonstrousSourceTriggeredAbility;
import mage.abilities.dynamicvalue.common.GetMonstrosityXValue;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.MonstrosityAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.TuskenRaiderToken;

import java.util.UUID;

/**
 *
 * @author Styxo
 */
public final class BanthaHerd extends CardImpl {

    public BanthaHerd(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{W}");
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {X}{W}{W}: Monstrosity X.
        this.addAbility(new MonstrosityAbility("{X}{W}{W}", Integer.MAX_VALUE));

        // When Batha Herd becomes monstrous, create X 1/1 white Tusken Raider tokens.
        this.addAbility(new BecomesMonstrousSourceTriggeredAbility(new CreateTokenEffect(new TuskenRaiderToken(), GetMonstrosityXValue.instance)));
    }

    private BanthaHerd(final BanthaHerd card) {
        super(card);
    }

    @Override
    public BanthaHerd copy() {
        return new BanthaHerd(this);
    }
}
