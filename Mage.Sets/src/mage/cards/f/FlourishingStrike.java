package mage.cards.f;

import mage.abilities.Mode;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.EntwineAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FlourishingStrike extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature with flying");

    static {
        filter.add(new AbilityPredicate(FlyingAbility.class));
    }

    public FlourishingStrike(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{G}");


        // Choose one —
        // • Flourishing Strike deals 5 damage to target creature with flying.
        this.getSpellAbility().addEffect(new DamageTargetEffect(5));
        this.getSpellAbility().addTarget(new TargetPermanent(filter));

        // • Target creature gets +3/+3 until end of turn.
        Mode mode = new Mode(new BoostTargetEffect(3, 3, Duration.EndOfTurn));
        mode.addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addMode(mode);

        // Entwine {2}{G}
        this.addAbility(new EntwineAbility("{2}{G}"));
    }

    private FlourishingStrike(final FlourishingStrike card) {
        super(card);
    }

    @Override
    public FlourishingStrike copy() {
        return new FlourishingStrike(this);
    }
}
