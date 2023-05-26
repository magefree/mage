package mage.cards.f;

import mage.MageInt;
import mage.abilities.common.LimitedTimesPerTurnActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Frostwalla extends CardImpl {

    public Frostwalla(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.supertype.add(SuperType.SNOW);
        this.subtype.add(SubType.LIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {S}: Frostwalla gets +2/+2 until end of turn. Activate this ability only once each turn.
        this.addAbility(new LimitedTimesPerTurnActivatedAbility(
                Zone.BATTLEFIELD,
                new BoostSourceEffect(
                        2, 2, Duration.EndOfTurn
                ), new ManaCostsImpl<>("{S}")
        ));
    }

    private Frostwalla(final Frostwalla card) {
        super(card);
    }

    @Override
    public Frostwalla copy() {
        return new Frostwalla(this);
    }
}
