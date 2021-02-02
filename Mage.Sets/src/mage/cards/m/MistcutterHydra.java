
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.CantBeCounteredSourceAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.common.EntersBattlefieldWithXCountersEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;

/**
 *
 * @author LevelX2
 */
public final class MistcutterHydra extends CardImpl {

    public MistcutterHydra(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{X}{G}");
        this.subtype.add(SubType.HYDRA);

        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Mistcutter Hydra can't be countered.
        this.addAbility(new CantBeCounteredSourceAbility());
        // Haste
        this.addAbility(HasteAbility.getInstance());
        // protection from blue
        this.addAbility(ProtectionAbility.from(ObjectColor.BLUE));
        // Mistcutter Hydra enters the battlefield with X +1/+1 counters on it.
        this.addAbility(new EntersBattlefieldAbility(new EntersBattlefieldWithXCountersEffect(CounterType.P1P1.createInstance())));
    }

    private MistcutterHydra(final MistcutterHydra card) {
        super(card);
    }

    @Override
    public MistcutterHydra copy() {
        return new MistcutterHydra(this);
    }
}
