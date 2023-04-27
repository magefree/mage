
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.CycleOrDiscardControllerTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;

/**
 *
 * @author Styxo
 */
public final class HorrorOfTheBrokenLands extends CardImpl {

    public HorrorOfTheBrokenLands(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}");

        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Whenever you cycle or discard another card, Horror of the Broken Lands gets +2/+1 until end of turn.
        this.addAbility(new CycleOrDiscardControllerTriggeredAbility(new BoostSourceEffect(2, 1, Duration.EndOfTurn)));

        // Cycling {B}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{B}")));

    }

    private HorrorOfTheBrokenLands(final HorrorOfTheBrokenLands card) {
        super(card);
    }

    @Override
    public HorrorOfTheBrokenLands copy() {
        return new HorrorOfTheBrokenLands(this);
    }
}
