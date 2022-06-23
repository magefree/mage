
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.PersistAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author Loki
 */
public final class RestlessApparition extends CardImpl {

    public RestlessApparition(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{W/B}{W/B}{W/B}");
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {WB}{WB}{WB}: Restless Apparition gets +3/+3 until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostSourceEffect(3, 3, Duration.EndOfTurn), new ManaCostsImpl<>("{W/B}{W/B}{W/B}")));
        // Persist
        this.addAbility(new PersistAbility());
    }

    private RestlessApparition(final RestlessApparition card) {
        super(card);
    }

    @Override
    public RestlessApparition copy() {
        return new RestlessApparition(this);
    }
}
