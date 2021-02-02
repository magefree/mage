
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
public final class SilkenfistFighter extends CardImpl {

    public SilkenfistFighter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}");
        this.subtype.add(SubType.KOR);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Whenever Silkenfist Fighter becomes blocked, untap it.
        Effect effect = new UntapSourceEffect();
        effect.setText("untap it");
        this.addAbility(new BecomesBlockedSourceTriggeredAbility(effect, false));
    }

    private SilkenfistFighter(final SilkenfistFighter card) {
        super(card);
    }

    @Override
    public SilkenfistFighter copy() {
        return new SilkenfistFighter(this);
    }
}
