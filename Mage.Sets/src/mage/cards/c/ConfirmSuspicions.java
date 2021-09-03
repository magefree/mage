package mage.cards.c;

import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.effects.keyword.InvestigateEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetSpell;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class ConfirmSuspicions extends CardImpl {

    public ConfirmSuspicions(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{U}{U}");

        // Counter target spell.
        getSpellAbility().addEffect(new CounterTargetEffect());
        getSpellAbility().addTarget(new TargetSpell());

        // Investigate three times.
        getSpellAbility().addEffect(new InvestigateEffect(3).concatBy("<br>"));
    }

    private ConfirmSuspicions(final ConfirmSuspicions card) {
        super(card);
    }

    @Override
    public ConfirmSuspicions copy() {
        return new ConfirmSuspicions(this);
    }
}
