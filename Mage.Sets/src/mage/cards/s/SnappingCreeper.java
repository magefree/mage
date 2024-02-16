

package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.LandfallAbility;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;

/**
 *
 * @author Loki
 */
public final class SnappingCreeper extends CardImpl {

    public SnappingCreeper (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}");
        this.subtype.add(SubType.PLANT);
        this.color.setGreen(true);        
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);
        this.addAbility(new LandfallAbility(new GainAbilitySourceEffect(VigilanceAbility.getInstance(), Duration.EndOfTurn), false));
    }

    private SnappingCreeper(final SnappingCreeper card) {
        super(card);
    }

    @Override
    public SnappingCreeper copy() {
        return new SnappingCreeper(this);
    }

}
