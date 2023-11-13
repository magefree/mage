package mage.cards.m;

import mage.MageInt;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.IslandcyclingAbility;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MaraudingBrinefang extends CardImpl {

    public MaraudingBrinefang(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{U}{U}");

        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(6);
        this.toughness = new MageInt(7);

        // Ward {3}
        this.addAbility(new WardAbility(new ManaCostsImpl<>("{3}")));

        // Islandcycling {2}
        this.addAbility(new IslandcyclingAbility(new ManaCostsImpl<>("{2}")));
    }

    private MaraudingBrinefang(final MaraudingBrinefang card) {
        super(card);
    }

    @Override
    public MaraudingBrinefang copy() {
        return new MaraudingBrinefang(this);
    }
}
