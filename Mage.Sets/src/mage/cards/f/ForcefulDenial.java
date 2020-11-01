package mage.cards.f;

import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.keyword.CascadeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetSpell;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ForcefulDenial extends CardImpl {

    public ForcefulDenial(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{U}{U}");

        // Cascade
        this.addAbility(new CascadeAbility());

        // Counter target spell.
        this.getSpellAbility().addEffect(new CounterTargetEffect());
        this.getSpellAbility().addTarget(new TargetSpell());
    }

    private ForcefulDenial(final ForcefulDenial card) {
        super(card);
    }

    @Override
    public ForcefulDenial copy() {
        return new ForcefulDenial(this);
    }
}
