package mage.cards.j;

import mage.MageInt;
import mage.abilities.common.DealtDamageToSourceTriggeredAbility;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.common.DamageControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class JackalPup extends CardImpl {

    public JackalPup(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}");
        this.subtype.add(SubType.JACKAL);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Whenever Jackal Pup is dealt damage, it deals that much damage to you.
        this.addAbility(new DealtDamageToSourceTriggeredAbility(
                new DamageControllerEffect(SavedDamageValue.MUCH, "it"), false));
    }

    private JackalPup(final JackalPup card) {
        super(card);
    }

    @Override
    public JackalPup copy() {
        return new JackalPup(this);
    }
}
