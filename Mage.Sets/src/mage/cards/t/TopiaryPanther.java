package mage.cards.t;

import mage.MageInt;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.BasicLandcyclingAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TopiaryPanther extends CardImpl {

    public TopiaryPanther(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}{G}");

        this.subtype.add(SubType.PLANT);
        this.subtype.add(SubType.CAT);
        this.power = new MageInt(6);
        this.toughness = new MageInt(5);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Basic landcycling {1}{G}
        this.addAbility(new BasicLandcyclingAbility(new ManaCostsImpl<>("{1}{G}")));
    }

    private TopiaryPanther(final TopiaryPanther card) {
        super(card);
    }

    @Override
    public TopiaryPanther copy() {
        return new TopiaryPanther(this);
    }
}
