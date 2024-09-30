package mage.cards.s;

import mage.MageInt;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.SwampcyclingAbility;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SpectralSnatcher extends CardImpl {

    public SpectralSnatcher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}{B}");

        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(6);
        this.toughness = new MageInt(5);

        // Ward--Discard a card.
        this.addAbility(new WardAbility(new DiscardCardCost()));

        // Swampcycling {2}
        this.addAbility(new SwampcyclingAbility(new ManaCostsImpl<>("{2}")));
    }

    private SpectralSnatcher(final SpectralSnatcher card) {
        super(card);
    }

    @Override
    public SpectralSnatcher copy() {
        return new SpectralSnatcher(this);
    }
}
