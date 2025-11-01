package mage.cards.f;

import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FancyFootwork extends CardImpl {

    public FancyFootwork(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{W}");

        this.subtype.add(SubType.LESSON);

        // Untap one or two target creatures. They each get +2/+2 until end of turn.
        this.getSpellAbility().addEffect(new UntapTargetEffect());
        this.getSpellAbility().addEffect(new BoostTargetEffect(2, 2).setText("They each get +2/+2 until end of turn"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(1, 2));
    }

    private FancyFootwork(final FancyFootwork card) {
        super(card);
    }

    @Override
    public FancyFootwork copy() {
        return new FancyFootwork(this);
    }
}
