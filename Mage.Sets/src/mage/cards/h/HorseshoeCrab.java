
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ColoredManaCost;
import mage.abilities.effects.common.UntapSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.ColoredManaSymbol;
import mage.constants.Zone;

/**
 *
 * @author Loki
 */
public final class HorseshoeCrab extends CardImpl {

    public HorseshoeCrab(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}");
        this.subtype.add(SubType.CRAB);

        this.power = new MageInt(1);
        this.toughness = new MageInt(3);
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new UntapSourceEffect(), new ColoredManaCost(ColoredManaSymbol.U)));
    }

    private HorseshoeCrab(final HorseshoeCrab card) {
        super(card);
    }

    @Override
    public HorseshoeCrab copy() {
        return new HorseshoeCrab(this);
    }
}
