
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksAndIsNotBlockedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.counter.AddPoisonCounterTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LoneFox
 */
public final class CryptCobra extends CardImpl {

    public CryptCobra(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}");
        this.subtype.add(SubType.SNAKE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever Crypt Cobra attacks and isn't blocked, defending player gets a poison counter.
        Effect effect = new AddPoisonCounterTargetEffect(1);
        effect.setText("defending player gets a poison counter");
        this.addAbility(new AttacksAndIsNotBlockedTriggeredAbility(effect, false, true));
    }

    private CryptCobra(final CryptCobra card) {
        super(card);
    }

    @Override
    public CryptCobra copy() {
        return new CryptCobra(this);
    }
}
