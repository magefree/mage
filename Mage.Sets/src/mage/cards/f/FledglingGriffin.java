

package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.LandfallAbility;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;

/**
 *
 * @author Loki
 */
public final class FledglingGriffin extends CardImpl {

    public FledglingGriffin (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}");
        this.subtype.add(SubType.GRIFFIN);
        this.color.setWhite(true);        
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        this.addAbility(new LandfallAbility(new GainAbilitySourceEffect(FlyingAbility.getInstance(), Duration.EndOfTurn), false));
    }

    private FledglingGriffin(final FledglingGriffin card) {
        super(card);
    }

    @Override
    public FledglingGriffin copy() {
        return new FledglingGriffin(this);
    }

}
