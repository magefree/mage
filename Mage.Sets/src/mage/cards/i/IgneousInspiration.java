package mage.cards.i;

import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.LearnEffect;
import mage.abilities.hint.common.OpenSideboardHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class IgneousInspiration extends CardImpl {

    public IgneousInspiration(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{R}");

        // Igneous Inspiration deals 3 damage to any target.
        this.getSpellAbility().addEffect(new DamageTargetEffect(3));
        this.getSpellAbility().addTarget(new TargetAnyTarget());

        // Learn.
        this.getSpellAbility().addEffect(new LearnEffect().concatBy("<br>"));
        this.getSpellAbility().addHint(OpenSideboardHint.instance);
    }

    private IgneousInspiration(final IgneousInspiration card) {
        super(card);
    }

    @Override
    public IgneousInspiration copy() {
        return new IgneousInspiration(this);
    }
}
