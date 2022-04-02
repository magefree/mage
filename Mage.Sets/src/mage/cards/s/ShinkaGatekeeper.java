package mage.cards.s;

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
 * @author LevelX2
 */
public final class ShinkaGatekeeper extends CardImpl {

    public ShinkaGatekeeper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");
        this.subtype.add(SubType.OGRE);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Whenever Shinka Gatekeeper is dealt damage, it deals that much damage to you.
        this.addAbility(new DealtDamageToSourceTriggeredAbility(
                new DamageControllerEffect(SavedDamageValue.MUCH, "it"), false));
    }

    private ShinkaGatekeeper(final ShinkaGatekeeper card) {
        super(card);
    }

    @Override
    public ShinkaGatekeeper copy() {
        return new ShinkaGatekeeper(this);
    }
}
