package mage.cards.i;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class InkriseInfiltrator extends CardImpl {

    public InkriseInfiltrator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.NINJA);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // {3}{B}: Inkrise Infiltrator gets +2/+2 until end of turn.
        this.addAbility(new SimpleActivatedAbility(new BoostSourceEffect(
                2, 2, Duration.EndOfTurn
        ), new ManaCostsImpl<>("{3}{B}")));
    }

    private InkriseInfiltrator(final InkriseInfiltrator card) {
        super(card);
    }

    @Override
    public InkriseInfiltrator copy() {
        return new InkriseInfiltrator(this);
    }
}
