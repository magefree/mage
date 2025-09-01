package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.CreaturesYouControlCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.SetBasePowerSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.game.permanent.token.AllyToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SukiKyoshiWarrior extends CardImpl {

    public SukiKyoshiWarrior(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G/W}{G/W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.subtype.add(SubType.ALLY);
        this.power = new MageInt(0);
        this.toughness = new MageInt(4);

        // Suki's power is equal to the number of creatures you control.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SetBasePowerSourceEffect(CreaturesYouControlCount.PLURAL)));

        // Whenever Suki attacks, create a 1/1 white Ally creature token that's tapped and attacking.
        this.addAbility(new AttacksTriggeredAbility(new CreateTokenEffect(new AllyToken(), 1, true, true)));
    }

    private SukiKyoshiWarrior(final SukiKyoshiWarrior card) {
        super(card);
    }

    @Override
    public SukiKyoshiWarrior copy() {
        return new SukiKyoshiWarrior(this);
    }
}
