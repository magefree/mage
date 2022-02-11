
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.CycleOrDiscardControllerTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author fireshoes
 */
public final class CuratorOfMysteries extends CardImpl {

    public CuratorOfMysteries(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{U}");

        this.subtype.add(SubType.SPHINX);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever you cycle or discard another card, scry 1.
        this.addAbility(new CycleOrDiscardControllerTriggeredAbility(new ScryEffect(1, false)).setTriggerPhrase("Whenever you cycle or discard another card, "));

        // Cycling {U}
        this.addAbility(new CyclingAbility(new ManaCostsImpl("{U}")));

    }

    private CuratorOfMysteries(final CuratorOfMysteries card) {
        super(card);
    }

    @Override
    public CuratorOfMysteries copy() {
        return new CuratorOfMysteries(this);
    }
}
