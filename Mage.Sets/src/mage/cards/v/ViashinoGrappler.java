
package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ColoredManaCost;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;

/**
 *
 * @author Loki
 */
public final class ViashinoGrappler extends CardImpl {

    public ViashinoGrappler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}");
        this.subtype.add(SubType.VIASHINO);

        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // {G}: Viashino Grappler gains trample until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainAbilitySourceEffect(TrampleAbility.getInstance(), Duration.EndOfTurn), new ColoredManaCost(ColoredManaSymbol.G)));
    }

    private ViashinoGrappler(final ViashinoGrappler card) {
        super(card);
    }

    @Override
    public ViashinoGrappler copy() {
        return new ViashinoGrappler(this);
    }
}
