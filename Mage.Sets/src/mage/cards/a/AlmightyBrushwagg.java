package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AlmightyBrushwagg extends CardImpl {

    public AlmightyBrushwagg(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}");

        this.subtype.add(SubType.BRUSHWAGG);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // {3}{G}: Almighty Brushwagg gets +3/+3 until end of turn.
        this.addAbility(new SimpleActivatedAbility(
                new BoostSourceEffect(3, 3, Duration.EndOfTurn), new ManaCostsImpl<>("{3}{G}")
        ));
    }

    private AlmightyBrushwagg(final AlmightyBrushwagg card) {
        super(card);
    }

    @Override
    public AlmightyBrushwagg copy() {
        return new AlmightyBrushwagg(this);
    }
}
