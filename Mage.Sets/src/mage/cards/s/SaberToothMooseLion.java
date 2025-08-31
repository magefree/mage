package mage.cards.s;

import mage.MageInt;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.ForestcyclingAbility;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SaberToothMooseLion extends CardImpl {

    public SaberToothMooseLion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}{G}");

        this.subtype.add(SubType.ELK);
        this.subtype.add(SubType.CAT);
        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Forestcycling {2}
        this.addAbility(new ForestcyclingAbility(new ManaCostsImpl<>("{2}")));
    }

    private SaberToothMooseLion(final SaberToothMooseLion card) {
        super(card);
    }

    @Override
    public SaberToothMooseLion copy() {
        return new SaberToothMooseLion(this);
    }
}
