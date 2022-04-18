
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.LimitedTimesPerTurnActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.MadnessAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 *
 * @author magenoxx_at_gmail.com
 */
public final class BaskingRootwalla extends CardImpl {

    public BaskingRootwalla(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{G}");
        this.subtype.add(SubType.LIZARD);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {1}{G}: Basking Rootwalla gets +2/+2 until end of turn. Activate this ability only once each turn.
        this.addAbility(new LimitedTimesPerTurnActivatedAbility(Zone.BATTLEFIELD,
                new BoostSourceEffect(2, 2, Duration.EndOfTurn), new ManaCostsImpl("{1}{G}")));

        // Madness {0}
        this.addAbility(new MadnessAbility(new ManaCostsImpl("{0}")));
    }

    private BaskingRootwalla(final BaskingRootwalla card) {
        super(card);
    }

    @Override
    public BaskingRootwalla copy() {
        return new BaskingRootwalla(this);
    }
}
