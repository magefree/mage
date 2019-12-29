
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealtDamageToSourceTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author TheElk801
 */
public final class RavenousDaggertooth extends CardImpl {

    public RavenousDaggertooth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Enrage - Whenever Ravenous Daggertooth is dealt damage, you gain 2 life.
        Ability ability = new DealtDamageToSourceTriggeredAbility(new GainLifeEffect(2), false, true);
        this.addAbility(ability);
    }

    public RavenousDaggertooth(final RavenousDaggertooth card) {
        super(card);
    }

    @Override
    public RavenousDaggertooth copy() {
        return new RavenousDaggertooth(this);
    }
}
