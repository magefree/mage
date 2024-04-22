package mage.cards.s;

import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.effects.keyword.AmassEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.TargetSpell;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SarumansTrickery extends CardImpl {

    public SarumansTrickery(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}{U}");

        // Counter target spell.
        this.getSpellAbility().addEffect(new CounterTargetEffect());
        this.getSpellAbility().addTarget(new TargetSpell());

        // Amass Orcs 1.
        this.getSpellAbility().addEffect(new AmassEffect(1, SubType.ORC).concatBy("<br>"));
    }

    private SarumansTrickery(final SarumansTrickery card) {
        super(card);
    }

    @Override
    public SarumansTrickery copy() {
        return new SarumansTrickery(this);
    }
}
