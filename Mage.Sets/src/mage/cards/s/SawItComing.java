package mage.cards.s;

import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.keyword.ForetellAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetSpell;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SawItComing extends CardImpl {

    public SawItComing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}{U}");

        // Counter target spell.
        this.getSpellAbility().addEffect(new CounterTargetEffect());
        this.getSpellAbility().addTarget(new TargetSpell());

        // Foretell {1}{U}
        this.addAbility(new ForetellAbility(this, "{1}{U}"));
    }

    private SawItComing(final SawItComing card) {
        super(card);
    }

    @Override
    public SawItComing copy() {
        return new SawItComing(this);
    }
}
