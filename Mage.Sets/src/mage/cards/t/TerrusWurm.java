
package mage.cards.t;

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
public final class TerrusWurm extends CardImpl {

    public TerrusWurm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{6}{B}");
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.WURM);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Scavenge {6}{B} ({6}{B}, Exile this card from your graveyard: Put a number of +1/+1 counters equal to this card's power on target creature. Scavenge only as a sorcery.)
        this.addAbility(new ScavengeAbility(new ManaCostsImpl<>("{6}{B}")));
    }

    private TerrusWurm(final TerrusWurm card) {
        super(card);
    }

    @Override
    public TerrusWurm copy() {
        return new TerrusWurm(this);
    }
}
