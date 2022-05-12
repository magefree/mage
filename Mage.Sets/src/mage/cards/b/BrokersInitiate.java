package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.SetPowerToughnessSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubLayer;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BrokersInitiate extends CardImpl {

    public BrokersInitiate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}");

        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.CITIZEN);
        this.power = new MageInt(0);
        this.toughness = new MageInt(4);

        // {4}{G/U}: Brokers Initiate has base power and toughness 5/5 until end of turn.
        this.addAbility(new SimpleActivatedAbility(new SetPowerToughnessSourceEffect(
                5, 5, Duration.EndOfTurn, SubLayer.SetPT_7b
        ), new ManaCostsImpl<>("{4}{G/U}")));
    }

    private BrokersInitiate(final BrokersInitiate card) {
        super(card);
    }

    @Override
    public BrokersInitiate copy() {
        return new BrokersInitiate(this);
    }
}
