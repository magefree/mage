package mage.cards.j;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerValue;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.RavenousAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.RabbitToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class JackedRabbit extends CardImpl {

    public JackedRabbit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{X}{1}{W}");

        this.subtype.add(SubType.RABBIT);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Ravenous
        this.addAbility(new RavenousAbility());

        // Whenever Jacked Rabbit attacks, create a number of 1/1 white Rabbit creature tokens equal to Jacked Rabbit's power.
        this.addAbility(new AttacksTriggeredAbility(new CreateTokenEffect(new RabbitToken(), SourcePermanentPowerValue.NOT_NEGATIVE)));
    }

    private JackedRabbit(final JackedRabbit card) {
        super(card);
    }

    @Override
    public JackedRabbit copy() {
        return new JackedRabbit(this);
    }
}
