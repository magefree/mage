package mage.cards.w;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.ConvokeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.WildfireAwakenerToken;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class WildfireAwakener extends CardImpl {

    public WildfireAwakener(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{X}{1}{R}{W}");
        this.subtype.add(SubType.HUMAN, SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Convoke
        this.addAbility(new ConvokeAbility());

        // When Wildfire Awakener enters the battlefield, create X 1/1 red Elemental creature tokens
        // with "Whenever this creature becomes tapped, it deals 1 damage to target player."
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new CreateTokenEffect(new WildfireAwakenerToken(), ManacostVariableValue.ETB)
        ));
    }

    private WildfireAwakener(final WildfireAwakener card) {
        super(card);
    }

    @Override
    public WildfireAwakener copy() {
        return new WildfireAwakener(this);
    }
}
