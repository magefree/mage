package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.abilities.costs.OrCost;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class TitaniaRuggedRumbler extends CardImpl {

    public TitaniaRuggedRumbler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B/G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // As an additional cost to cast this spell, discard a card or pay {2}.
        this.getSpellAbility().addCost(new OrCost(
            "discard a card or pay {2}",
            new DiscardCardCost(),
            new ManaCostsImpl<>("{2}")
        ));

        // Ward--Discard a card or pay {2}.
        this.addAbility(new WardAbility(new OrCost(
            "discard a card or pay {2}",
            new DiscardCardCost(),
            new ManaCostsImpl<>("{2}")
        )));
    }

    private TitaniaRuggedRumbler(final TitaniaRuggedRumbler card) {
        super(card);
    }

    @Override
    public TitaniaRuggedRumbler copy() {
        return new TitaniaRuggedRumbler(this);
    }
}
