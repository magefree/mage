
package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.combat.CanBlockAdditionalCreatureEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 *
 * @author LoneFox
 */
public final class ForiysianInterceptor extends CardImpl {

    public ForiysianInterceptor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(0);
        this.toughness = new MageInt(5);

        // Flash
        this.addAbility(FlashAbility.getInstance());
        // Defender
        this.addAbility(DefenderAbility.getInstance());
        // Foriysian Interceptor can block an additional creature each combat.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CanBlockAdditionalCreatureEffect()));
    }

    private ForiysianInterceptor(final ForiysianInterceptor card) {
        super(card);
    }

    @Override
    public ForiysianInterceptor copy() {
        return new ForiysianInterceptor(this);
    }
}
