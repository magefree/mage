package mage.cards.c;

import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BecomesXXConstructSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;

import java.util.UUID;

/**
 * @author Backfir3
 */
public final class ChimericStaff extends CardImpl {

    public ChimericStaff(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        // {X}: Chimeric Staff becomes an X/X Construct artifact creature until end of turn.
        this.addAbility(new SimpleActivatedAbility(new BecomesXXConstructSourceEffect(Duration.EndOfTurn), new ManaCostsImpl<>("{X}")));
    }

    private ChimericStaff(final ChimericStaff card) {
        super(card);
    }

    @Override
    public ChimericStaff copy() {
        return new ChimericStaff(this);
    }
}
