
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.combat.CanBlockAdditionalCreatureEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author LevelX2
 */
public final class CoastlineChimera extends CardImpl {

    public CoastlineChimera(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{U}");
        this.subtype.add(SubType.CHIMERA);

        this.power = new MageInt(1);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // {1}{W}: Coastline Chimera can block an additional creature this turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new CanBlockAdditionalCreatureEffect(Duration.EndOfTurn, 1), new ManaCostsImpl<>("{1}{W}")));
    }

    private CoastlineChimera(final CoastlineChimera card) {
        super(card);
    }

    @Override
    public CoastlineChimera copy() {
        return new CoastlineChimera(this);
    }
}
