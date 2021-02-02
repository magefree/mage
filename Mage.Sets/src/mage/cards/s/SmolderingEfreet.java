
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.DamageControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author fireshoes
 */
public final class SmolderingEfreet extends CardImpl {

    public SmolderingEfreet(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}");
        this.subtype.add(SubType.EFREET);
        this.subtype.add(SubType.MONK);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Smoldering Efreet dies, it deals 2 damage to you.
        this.addAbility(new DiesSourceTriggeredAbility(new DamageControllerEffect(2, "it"), false));
    }

    private SmolderingEfreet(final SmolderingEfreet card) {
        super(card);
    }

    @Override
    public SmolderingEfreet copy() {
        return new SmolderingEfreet(this);
    }
}
