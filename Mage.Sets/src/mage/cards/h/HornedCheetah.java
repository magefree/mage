package mage.cards.h;

import mage.MageInt;
import mage.abilities.common.DealsDamageSourceTriggeredAbility;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author LoneFox
 */
public final class HornedCheetah extends CardImpl {

    public HornedCheetah(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{W}");
        this.subtype.add(SubType.CAT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Horned Cheetah deals damage, you gain that much life.
        this.addAbility(new DealsDamageSourceTriggeredAbility(new GainLifeEffect(SavedDamageValue.MUCH)));
    }

    private HornedCheetah(final HornedCheetah card) {
        super(card);
    }

    @Override
    public HornedCheetah copy() {
        return new HornedCheetah(this);
    }
}
