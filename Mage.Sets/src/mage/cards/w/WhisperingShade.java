
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.SwampwalkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author cbt33
 */
public final class WhisperingShade extends CardImpl {

    public WhisperingShade(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}");
        this.subtype.add(SubType.SHADE);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Swampwalk
        this.addAbility(new SwampwalkAbility());
        // {B}: Whispering Shade gets +1/+1 until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostSourceEffect(1,1,Duration.EndOfTurn), new ManaCostsImpl<>("{B}")));
    }

    private WhisperingShade(final WhisperingShade card) {
        super(card);
    }

    @Override
    public WhisperingShade copy() {
        return new WhisperingShade(this);
    }
}
