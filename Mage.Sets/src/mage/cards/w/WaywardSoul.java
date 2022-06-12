
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.PutOnLibrarySourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 *
 * @author fireshoes
 */
public final class WaywardSoul extends CardImpl {

    public WaywardSoul(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}{U}");
        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        
        // {U}: Put Wayward Soul on top of its owner's library.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new PutOnLibrarySourceEffect(true), new ManaCostsImpl<>("{U}")));
    }

    private WaywardSoul(final WaywardSoul card) {
        super(card);
    }

    @Override
    public WaywardSoul copy() {
        return new WaywardSoul(this);
    }
}
