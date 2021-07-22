
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.DomainValue;
import mage.abilities.effects.common.continuous.SetToughnessSourceEffect;
import mage.abilities.hint.common.DomainHint;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 *
 * @author jeffwadsworth
 */
public final class AvenTrailblazer extends CardImpl {

    public AvenTrailblazer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}");
        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(0);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        
        // Domain - Aven Trailblazer's toughness is equal to the number of basic land types among lands you control.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SetToughnessSourceEffect(new DomainValue(), Duration.EndOfGame)).addHint(DomainHint.instance));
        
    }

    private AvenTrailblazer(final AvenTrailblazer card) {
        super(card);
    }

    @Override
    public AvenTrailblazer copy() {
        return new AvenTrailblazer(this);
    }
}
