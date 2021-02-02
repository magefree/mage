
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BecomesBlockedSourceTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.UntapSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LoneFox
 */
public final class SilkenfistOrder extends CardImpl {

    public SilkenfistOrder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}{W}");
        this.subtype.add(SubType.KOR);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // Whenever Silkenfist Order becomes blocked, untap it.
        Effect effect = new UntapSourceEffect();
        effect.setText("untap it");
        this.addAbility(new BecomesBlockedSourceTriggeredAbility(effect, false));
    }

    private SilkenfistOrder(final SilkenfistOrder card) {
        super(card);
    }

    @Override
    public SilkenfistOrder copy() {
        return new SilkenfistOrder(this);
    }
}
