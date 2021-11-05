
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.LimitedTimesPerTurnActivatedAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 *
 * @author fireshoes
 */
public final class HeirOfFalkenrath extends CardImpl {

    public HeirOfFalkenrath(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}");
        this.subtype.add(SubType.VAMPIRE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        this.secondSideCardClazz = HeirToTheNight.class;

        // Discard a card: Transform Heir of Falkenrath. Activate this ability only once each turn.
        this.addAbility(new TransformAbility());
        this.addAbility(new LimitedTimesPerTurnActivatedAbility(Zone.BATTLEFIELD, new TransformSourceEffect(), new DiscardCardCost()));
    }

    private HeirOfFalkenrath(final HeirOfFalkenrath card) {
        super(card);
    }

    @Override
    public HeirOfFalkenrath copy() {
        return new HeirOfFalkenrath(this);
    }
}
