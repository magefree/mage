package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.SoldierToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SergeantAtArms extends CardImpl {

    public SergeantAtArms(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Kicker {2}{W}
        this.addAbility(new KickerAbility("{2}{W}"));

        // When Sergeant-at-Arms enters the battlefield, if it was kicked, create two 1/1 white soldier creature tokens.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new SoldierToken(), 2))
                .withInterveningIf(KickedCondition.ONCE));
    }

    private SergeantAtArms(final SergeantAtArms card) {
        super(card);
    }

    @Override
    public SergeantAtArms copy() {
        return new SergeantAtArms(this);
    }
}
