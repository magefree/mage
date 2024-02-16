package mage.cards.w;

import mage.MageInt;
import mage.abilities.common.DealtDamageToSourceTriggeredAbility;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 *
 * @author fireshoes
 */
public final class WallOfHope extends CardImpl {

    public WallOfHope(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{W}");
        this.subtype.add(SubType.WALL);
        this.power = new MageInt(0);
        this.toughness = new MageInt(3);

        // Defender
        this.addAbility(DefenderAbility.getInstance());
        // Whenever Wall of Hope is dealt damage, you gain that much life.
        this.addAbility(new DealtDamageToSourceTriggeredAbility(new GainLifeEffect(SavedDamageValue.MUCH), false));

    }

    private WallOfHope(final WallOfHope card) {
        super(card);
    }

    @Override
    public WallOfHope copy() {
        return new WallOfHope(this);
    }
}
