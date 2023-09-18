
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.ScavengeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author magenoxx_at_gmail.com
 */
public final class DregMangler extends CardImpl {

    public DregMangler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}{G}");
        this.subtype.add(SubType.PLANT);
        this.subtype.add(SubType.ZOMBIE);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Haste
        this.addAbility(HasteAbility.getInstance());
        // Scavenge {3}{B}{G}
        this.addAbility(new ScavengeAbility(new ManaCostsImpl<>("{3}{B}{G}")));
    }

    private DregMangler(final DregMangler card) {
        super(card);
    }

    @Override
    public DregMangler copy() {
        return new DregMangler(this);
    }
}
