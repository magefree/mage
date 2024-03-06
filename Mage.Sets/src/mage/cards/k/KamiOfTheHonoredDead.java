package mage.cards.k;

import mage.MageInt;
import mage.abilities.common.DealtDamageToSourceTriggeredAbility;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.SoulshiftAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 *
 * @author LevelX
 */
public final class KamiOfTheHonoredDead extends CardImpl {

    public KamiOfTheHonoredDead(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{W}{W}");
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // Flying  
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Kami of the Honored Dead is dealt damage, you gain that much life.
        this.addAbility(new DealtDamageToSourceTriggeredAbility(new GainLifeEffect(SavedDamageValue.MUCH), false));

        // Soulshift 6 (When this creature dies, you may return target Spirit card with converted mana cost 6 or less from your graveyard to your hand.)
        this.addAbility(new SoulshiftAbility(6));
    }

    private KamiOfTheHonoredDead(final KamiOfTheHonoredDead card) {
        super(card);
    }

    @Override
    public KamiOfTheHonoredDead copy() {
        return new KamiOfTheHonoredDead(this);
    }
}
