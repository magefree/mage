package mage.cards.r;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.DevotionCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.HumanSoldierToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ReverentHoplite extends CardImpl {

    public ReverentHoplite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // When Reverent Hoplite enters the battlefield, create a number of 1/1 white Human Soldier creature tokens equal to your devotion to white.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new CreateTokenEffect(
                        new HumanSoldierToken(), DevotionCount.W
                ).setText("create a number of 1/1 white Human Soldier creature tokens equal to your devotion to white")
        ).addHint(DevotionCount.W.getHint()));
    }

    private ReverentHoplite(final ReverentHoplite card) {
        super(card);
    }

    @Override
    public ReverentHoplite copy() {
        return new ReverentHoplite(this);
    }
}
