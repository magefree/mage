
package mage.cards.t;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.DevotionCount;
import mage.abilities.effects.common.CounterUnlessPaysEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ColoredManaSymbol;
import mage.target.TargetSpell;

/**
 *
 * @author LevelX2
 */
public final class ThassasRebuff extends CardImpl {

    public ThassasRebuff(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{U}");


        // Counter target spell unless its controller pays {X}, where X is your devotion to blue.
        this.getSpellAbility().addEffect(new CounterUnlessPaysEffect(new DevotionCount(ColoredManaSymbol.U)));
        this.getSpellAbility().addTarget(new TargetSpell());
    }

    public ThassasRebuff(final ThassasRebuff card) {
        super(card);
    }

    @Override
    public ThassasRebuff copy() {
        return new ThassasRebuff(this);
    }
}
