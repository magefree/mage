
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DamageAsThoughNotBlockedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.RegenerateSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 *
 * @author Backfir3
 */
public final class Rhox extends CardImpl {

    public Rhox(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{G}{G}");
        this.subtype.add(SubType.RHINO);
        this.subtype.add(SubType.BEAST);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // You may have Rhox assign its combat damage as though it weren't blocked.
        this.addAbility(DamageAsThoughNotBlockedAbility.getInstance());
        // {2}{G}: Regenerate Rhox.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new RegenerateSourceEffect(), new ManaCostsImpl<>("{2}{G}")));
    }

    private Rhox(final Rhox card) {
        super(card);
    }

    @Override
    public Rhox copy() {
        return new Rhox(this);
    }
}
