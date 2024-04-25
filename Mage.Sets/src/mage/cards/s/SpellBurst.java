
package mage.cards.s;

import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.keyword.BuybackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetSpell;
import mage.target.targetadjustment.XManaValueTargetAdjuster;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class SpellBurst extends CardImpl {

    public SpellBurst(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{X}{U}");

        // Buyback {3}
        this.addAbility(new BuybackAbility("{3}"));

        // Counter target spell with converted mana cost X.
        this.getSpellAbility().addEffect(new CounterTargetEffect().setText("counter target spell with mana value X"));
        this.getSpellAbility().setTargetAdjuster(new XManaValueTargetAdjuster());
        this.getSpellAbility().addTarget(new TargetSpell());
    }

    private SpellBurst(final SpellBurst card) {
        super(card);
    }

    @Override
    public SpellBurst copy() {
        return new SpellBurst(this);
    }
}
