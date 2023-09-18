
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BecomesColorSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 *
 * @author LoneFox
 */
public final class AncientKavu extends CardImpl {

    public AncientKavu(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}");
        this.subtype.add(SubType.KAVU);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // {2}: Ancient Kavu becomes colorless until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BecomesColorSourceEffect(ObjectColor.COLORLESS, Duration.EndOfTurn), new ManaCostsImpl<>("{2}")));
    }

    private AncientKavu(final AncientKavu card) {
        super(card);
    }

    @Override
    public AncientKavu copy() {
        return new AncientKavu(this);
    }
}
