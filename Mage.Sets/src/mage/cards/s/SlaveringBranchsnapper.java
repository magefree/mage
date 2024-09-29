package mage.cards.s;

import mage.MageInt;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.ForestcyclingAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SlaveringBranchsnapper extends CardImpl {

    public SlaveringBranchsnapper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}{G}");

        this.subtype.add(SubType.LIZARD);
        this.power = new MageInt(7);
        this.toughness = new MageInt(6);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Forestcycling {2}
        this.addAbility(new ForestcyclingAbility(new ManaCostsImpl<>("{2}")));
    }

    private SlaveringBranchsnapper(final SlaveringBranchsnapper card) {
        super(card);
    }

    @Override
    public SlaveringBranchsnapper copy() {
        return new SlaveringBranchsnapper(this);
    }
}
