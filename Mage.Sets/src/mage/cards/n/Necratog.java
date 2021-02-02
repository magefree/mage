
package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.ExileTopCreatureCardOfGraveyardCost;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author fireshoes
 */
public final class Necratog extends CardImpl {

    public Necratog(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}{B}");
        this.subtype.add(SubType.ATOG);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Exile the top creature card of your graveyard: Necratog gets +2/+2 until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostSourceEffect(2, 2, Duration.EndOfTurn), new ExileTopCreatureCardOfGraveyardCost(1)));
    }

    private Necratog(final Necratog card) {
        super(card);
    }

    @Override
    public Necratog copy() {
        return new Necratog(this);
    }
}