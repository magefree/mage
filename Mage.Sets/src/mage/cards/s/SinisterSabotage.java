package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetSpell;

/**
 *
 * @author TheElk801
 */
public final class SinisterSabotage extends CardImpl {

    public SinisterSabotage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}{U}");

        // Counter target spell.
        this.getSpellAbility().addEffect(new CounterTargetEffect());
        this.getSpellAbility().addTarget(new TargetSpell());

        // Surveil 1.
        this.getSpellAbility().addEffect(new SurveilEffect(1).concatBy("<br>"));
    }

    private SinisterSabotage(final SinisterSabotage card) {
        super(card);
    }

    @Override
    public SinisterSabotage copy() {
        return new SinisterSabotage(this);
    }
}
