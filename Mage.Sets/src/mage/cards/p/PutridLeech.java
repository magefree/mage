

package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.LimitedTimesPerTurnActivatedAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;


/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class PutridLeech extends CardImpl {

    public PutridLeech(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{B}{G}");
        

        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.LEECH);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        this.addAbility(new LimitedTimesPerTurnActivatedAbility(Zone.BATTLEFIELD, new BoostSourceEffect(2, 2, Duration.EndOfTurn), new PayLifeCost(2)));
    }

    private PutridLeech(final PutridLeech card) {
        super(card);
    }

    @Override
    public PutridLeech copy() {
        return new PutridLeech(this);
    }

}
