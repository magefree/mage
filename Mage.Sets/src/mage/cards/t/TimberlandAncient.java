package mage.cards.t;

import mage.MageInt;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.ForestcyclingAbility;
import mage.abilities.keyword.ReachAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TimberlandAncient extends CardImpl {

    public TimberlandAncient(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}{G}");

        this.subtype.add(SubType.TREEFOLK);
        this.power = new MageInt(6);
        this.toughness = new MageInt(5);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Forestcycling {2}
        this.addAbility(new ForestcyclingAbility(new ManaCostsImpl<>("{2}")));
    }

    private TimberlandAncient(final TimberlandAncient card) {
        super(card);
    }

    @Override
    public TimberlandAncient copy() {
        return new TimberlandAncient(this);
    }
}
