
package mage.cards.f;

import mage.abilities.Mode;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.RemoveCounterTargetEffect;
import mage.abilities.keyword.SuspendAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.target.common.TargetArtifactPermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetPermanentOrSuspendedCard;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class FuryCharm extends CardImpl {

    private static final FilterCard filter = new FilterCard("suspended card");
    static {
        filter.add(CounterType.TIME.getPredicate());
        filter.add(new AbilityPredicate(SuspendAbility.class));
    }

    public FuryCharm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{R}");

        // Choose one -
        this.getSpellAbility().getModes().setMinModes(1);
        this.getSpellAbility().getModes().setMaxModes(1);
        // Destroy target artifact;
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetArtifactPermanent());
        // or target creature gets +1/+1 and gains trample until end of turn;
        Effect effect = new BoostTargetEffect(1,1, Duration.EndOfTurn);
        effect.setText("target creature gets +1/+1");
        Mode mode = new Mode(effect);
        effect = new GainAbilityTargetEffect(TrampleAbility.getInstance(),Duration.EndOfTurn);
        effect.setText("and gains trample until end of turn");
        mode.addEffect(effect);
        mode.addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addMode(mode);
        // or remove two time counters from target permanent or suspended card.
        mode = new Mode(new RemoveCounterTargetEffect(CounterType.TIME.createInstance(2)));
        mode.addTarget(new TargetPermanentOrSuspendedCard());
        this.getSpellAbility().addMode(mode);
    }

    private FuryCharm(final FuryCharm card) {
        super(card);
    }

    @Override
    public FuryCharm copy() {
        return new FuryCharm(this);
    }
}
