
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author fireshoes
 */
public final class TatteredMummy extends CardImpl {

    public TatteredMummy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.JACKAL);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // When Tattered Mummy dies, each opponent loses 2 life.
        this.addAbility(new DiesSourceTriggeredAbility(new LoseLifeOpponentsEffect(2)));
    }

    private TatteredMummy(final TatteredMummy card) {
        super(card);
    }

    @Override
    public TatteredMummy copy() {
        return new TatteredMummy(this);
    }
}
