
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 *
 * @author fireshoes
 */
public final class WatchfulAutomaton extends CardImpl {

    public WatchfulAutomaton(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}");

        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {2}{U}: Scry 1.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new ScryEffect(1), new ManaCostsImpl<>("{2}{U}")));
    }

    private WatchfulAutomaton(final WatchfulAutomaton card) {
        super(card);
    }

    @Override
    public WatchfulAutomaton copy() {
        return new WatchfulAutomaton(this);
    }
}
