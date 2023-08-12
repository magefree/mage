
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SourceBecomesTargetTriggeredAbility;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.constants.SubType;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author TheElk801
 */
public final class Mirozel extends CardImpl {

    public Mirozel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.subtype.add(SubType.ILLUSION);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Mirozel becomes the target of a spell or ability, return Mirozel to its owner's hand.
        this.addAbility(new SourceBecomesTargetTriggeredAbility(new ReturnToHandSourceEffect(true)));
    }

    private Mirozel(final Mirozel card) {
        super(card);
    }

    @Override
    public Mirozel copy() {
        return new Mirozel(this);
    }
}
