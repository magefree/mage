package mage.cards.f;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.CreaturesYouControlCount;
import mage.abilities.effects.common.continuous.SetBasePowerSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FreedomFighterRecruit extends CardImpl {

    public FreedomFighterRecruit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.REBEL);
        this.subtype.add(SubType.ALLY);
        this.power = new MageInt(0);
        this.toughness = new MageInt(2);

        // Freedom Fighter Recruit's power is equal to the number of creatures you control.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SetBasePowerSourceEffect(CreaturesYouControlCount.PLURAL)));
    }

    private FreedomFighterRecruit(final FreedomFighterRecruit card) {
        super(card);
    }

    @Override
    public FreedomFighterRecruit copy() {
        return new FreedomFighterRecruit(this);
    }
}
