package mage.cards.n;

import java.util.UUID;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SuperType;

/**
 *
 * @author Loki
 */
public final class NightOfSoulsBetrayal extends CardImpl {

    public NightOfSoulsBetrayal (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{B}{B}");
        addSuperType(SuperType.LEGENDARY);

        this.addAbility(new SimpleStaticAbility(new BoostAllEffect(-1, -1, Duration.WhileOnBattlefield)));
    }

    public NightOfSoulsBetrayal (final NightOfSoulsBetrayal card) {
        super(card);
    }

    @Override
    public NightOfSoulsBetrayal copy() {
        return new NightOfSoulsBetrayal(this);
    }

}
