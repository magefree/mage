package mage.cards.t;

import mage.abilities.Mode;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.effects.keyword.ManifestDreadEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetSpell;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TwistReality extends CardImpl {

    public TwistReality(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}{U}");

        // Choose one --
        // * Counter target spell.
        this.getSpellAbility().addEffect(new CounterTargetEffect());
        this.getSpellAbility().addTarget(new TargetSpell());

        // * Manifest dread.
        this.getSpellAbility().addMode(new Mode(new ManifestDreadEffect()));
    }

    private TwistReality(final TwistReality card) {
        super(card);
    }

    @Override
    public TwistReality copy() {
        return new TwistReality(this);
    }
}
