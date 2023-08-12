package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.SourceBecomesTargetTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author North
 */
public final class AngelicProtector extends CardImpl {

    public AngelicProtector(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");
        this.subtype.add(SubType.ANGEL);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(new SourceBecomesTargetTriggeredAbility(
                new BoostSourceEffect(0, 3, Duration.EndOfTurn)
        ).setTriggerPhrase("Whenever {this} becomes the target of a spell or ability, "));
    }

    private AngelicProtector(final AngelicProtector card) {
        super(card);
    }

    @Override
    public AngelicProtector copy() {
        return new AngelicProtector(this);
    }
}
