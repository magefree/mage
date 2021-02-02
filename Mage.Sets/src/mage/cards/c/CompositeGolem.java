
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.Mana;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 *
 * @author Loki
 */
public final class CompositeGolem extends CardImpl {

    public CompositeGolem(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{6}");
        this.subtype.add(SubType.GOLEM);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Sacrifice Composite Golem: Add {W}{U}{B}{R}{G}.
        this.addAbility(new SimpleManaAbility(Zone.BATTLEFIELD, new Mana(1, 1, 1, 1, 1, 0, 0, 0), new SacrificeSourceCost()));
    }

    private CompositeGolem(final CompositeGolem card) {
        super(card);
    }

    @Override
    public CompositeGolem copy() {
        return new CompositeGolem(this);
    }
}
