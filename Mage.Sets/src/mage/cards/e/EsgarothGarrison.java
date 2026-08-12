package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.CreaturesYouControlCount;
import mage.abilities.effects.common.continuous.SetBasePowerSourceEffect;
import mage.abilities.effects.keyword.RecruitEffect;
import mage.abilities.hint.common.CreaturesYouControlHint;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 * @author muz
 */
public final class EsgarothGarrison extends CardImpl {

    public EsgarothGarrison(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(0);
        this.toughness = new MageInt(5);

        // Esgaroth Garrison's power is equal to the number of creatures you control.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SetBasePowerSourceEffect(
            CreaturesYouControlCount.PLURAL)).addHint(CreaturesYouControlHint.instance)
        );

        // When this creature enters, recruit.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new RecruitEffect()));
    }

    private EsgarothGarrison(final EsgarothGarrison card) {
        super(card);
    }

    @Override
    public EsgarothGarrison copy() {
        return new EsgarothGarrison(this);
    }
}
