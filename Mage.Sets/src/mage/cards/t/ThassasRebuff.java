package mage.cards.t;

import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.DevotionCount;
import mage.abilities.effects.common.CounterUnlessPaysEffect;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ColoredManaSymbol;
import mage.target.TargetSpell;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class ThassasRebuff extends CardImpl {

    private static final DynamicValue xValue = new DevotionCount(ColoredManaSymbol.U);

    public ThassasRebuff(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Counter target spell unless its controller pays {X}, where X is your devotion to blue.
        this.getSpellAbility().addEffect(new CounterUnlessPaysEffect(xValue));
        this.getSpellAbility().addTarget(new TargetSpell());
        this.getSpellAbility().addHint(new ValueHint("Devotion to blue", xValue));
    }

    public ThassasRebuff(final ThassasRebuff card) {
        super(card);
    }

    @Override
    public ThassasRebuff copy() {
        return new ThassasRebuff(this);
    }
}
