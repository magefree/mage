
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.PersistAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class KitchenFinks extends CardImpl {

    public KitchenFinks(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G/W}{G/W}");
        this.subtype.add(SubType.OUPHE);

        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // When Kitchen Finks enters the battlefield, you gain 2 life.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GainLifeEffect(2)));
        // Persist (When this creature dies, if it had no -1/-1 counters on it, return it to the battlefield under its owner's control with a -1/-1 counter on it.)
        this.addAbility(new PersistAbility());
    }

    private KitchenFinks(final KitchenFinks card) {
        super(card);
    }

    @Override
    public KitchenFinks copy() {
        return new KitchenFinks(this);
    }
}
