package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author anonymous
 */
public final class TheMotherlodeExcavator extends CardImpl {

    public TheMotherlodeExcavator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}{R}{R}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ROBOT);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // When The Motherlode, Excavator enters the battlefield, choose target opponent. You get an amount of {E} equal to the number of nonbasic lands that player controls.
        // Whenever The Motherlode attacks, you may pay {E}{E}{E}{E}. When you do, destroy target nonbasic land defending player controls, and creatures that player controls without flying can't block this turn.
    }

    private TheMotherlodeExcavator(final TheMotherlodeExcavator card) {
        super(card);
    }

    @Override
    public TheMotherlodeExcavator copy() {
        return new TheMotherlodeExcavator(this);
    }
}
