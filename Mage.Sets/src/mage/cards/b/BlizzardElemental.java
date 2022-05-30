
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.UntapSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 *
 * @author Backfir3
 */
public final class BlizzardElemental extends CardImpl {

    public BlizzardElemental(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{U}{U}");
        this.subtype.add(SubType.ELEMENTAL);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        this.addAbility(FlyingAbility.getInstance());
        // {3}{U}: Untap Blizzard Elemental.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new UntapSourceEffect(), new ManaCostsImpl<>("{3}{U}")));
    }

    private BlizzardElemental(final BlizzardElemental card) {
        super(card);
    }

    @Override
    public BlizzardElemental copy() {
        return new BlizzardElemental(this);
    }
}
