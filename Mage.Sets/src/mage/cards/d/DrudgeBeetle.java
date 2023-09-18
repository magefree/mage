
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.ScavengeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class DrudgeBeetle extends CardImpl {

    public DrudgeBeetle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}");
        this.subtype.add(SubType.INSECT);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Scavenge {5}{G} ({5}{G}, Exile this card from your graveyard: Put a number of +1/+1 counters equal to this card's power on target creature. Scavenge only as a sorcery.)
        this.addAbility(new ScavengeAbility(new ManaCostsImpl<>("{5}{G}")));
    }

    private DrudgeBeetle(final DrudgeBeetle card) {
        super(card);
    }

    @Override
    public DrudgeBeetle copy() {
        return new DrudgeBeetle(this);
    }
}
