package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.DealsDamageSourceTriggeredAbility;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author North
 */
public final class MourningThrull extends CardImpl {

    public MourningThrull(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W/B}");
        this.subtype.add(SubType.THRULL);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Mourning Thrull deals damage, you gain that much life.
        this.addAbility(new DealsDamageSourceTriggeredAbility(new GainLifeEffect(SavedDamageValue.MUCH)));
    }

    private MourningThrull(final MourningThrull card) {
        super(card);
    }

    @Override
    public MourningThrull copy() {
        return new MourningThrull(this);
    }
}
