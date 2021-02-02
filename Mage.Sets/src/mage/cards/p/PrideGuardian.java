

package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BlocksSourceTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author Loki
 */
public final class PrideGuardian extends CardImpl {

    public PrideGuardian(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{W}");
        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.MONK);

        this.power = new MageInt(0);
        this.toughness = new MageInt(3);
        
        this.addAbility(DefenderAbility.getInstance());
        // Whenever Pride Guardian blocks, you gain 3 life.
        this.addAbility(new BlocksSourceTriggeredAbility(new GainLifeEffect(3), false));
    }

    private PrideGuardian(final PrideGuardian card) {
        super(card);
    }

    @Override
    public PrideGuardian copy() {
        return new PrideGuardian(this);
    }

}
