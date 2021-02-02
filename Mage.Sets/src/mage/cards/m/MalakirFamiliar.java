
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.GainLifeControllerTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;

/**
 *
 * @author LevelX2
 */
public final class MalakirFamiliar extends CardImpl {

    public MalakirFamiliar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}");
        this.subtype.add(SubType.BAT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Deahtouch
        this.addAbility(DeathtouchAbility.getInstance());
        // Whenever you gain life, Malakir Familiar gets +1/+1 until end of turn.
        this.addAbility(new GainLifeControllerTriggeredAbility(new BoostSourceEffect(1, 1, Duration.EndOfTurn), false));

    }

    private MalakirFamiliar(final MalakirFamiliar card) {
        super(card);
    }

    @Override
    public MalakirFamiliar copy() {
        return new MalakirFamiliar(this);
    }
}
