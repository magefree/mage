
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.MadnessAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author magenoxx_at_gmail.com
 */
public final class RecklessWurm extends CardImpl {

    public RecklessWurm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}{R}");
        this.subtype.add(SubType.WURM);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Madness {2}{R}
        this.addAbility(new MadnessAbility(new ManaCostsImpl<>("{2}{R}")));
    }

    private RecklessWurm(final RecklessWurm card) {
        super(card);
    }

    @Override
    public RecklessWurm copy() {
        return new RecklessWurm(this);
    }
}
