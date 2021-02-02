
package mage.cards.z;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.BloodrushAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class ZhurTaaSwine extends CardImpl {

    public ZhurTaaSwine(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}{G}");
        this.subtype.add(SubType.BOAR);

        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Bloodrush - 1{R}{G}, Discard Zhur-Taa Swine: Target attacking creature gets +5/+4 until end of turn.
        this.addAbility(new BloodrushAbility("{1}{R}{G}", new BoostTargetEffect(5,4, Duration.EndOfTurn)));


    }

    private ZhurTaaSwine(final ZhurTaaSwine card) {
        super(card);
    }

    @Override
    public ZhurTaaSwine copy() {
        return new ZhurTaaSwine(this);
    }
}
