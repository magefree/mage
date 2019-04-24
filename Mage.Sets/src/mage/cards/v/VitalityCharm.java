
package mage.cards.v;

import java.util.UUID;
import mage.abilities.Mode;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.RegenerateTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.permanent.token.InsectToken;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LoneFox
 */
public final class VitalityCharm extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("Beast");

    static {
        filter.add(new SubtypePredicate(SubType.BEAST));
    }

    public VitalityCharm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{G}");

        // Choose one - Create a 1/1 green Insect creature token
        this.getSpellAbility().addEffect(new CreateTokenEffect(new InsectToken()));
        // or target creature gets +1/+1 and gains trample until end of turn
        Mode mode = new Mode();
        Effect effect = new BoostTargetEffect(1, 1, Duration.EndOfTurn);
        effect.setText("target creature gets +1/+1");
        mode.getEffects().add(effect);
        effect = new GainAbilityTargetEffect(TrampleAbility.getInstance(), Duration.EndOfTurn);
        effect.setText("and gains trample until end of turn");
        mode.getEffects().add(effect);
        mode.getTargets().add(new TargetCreaturePermanent());
        this.getSpellAbility().addMode(mode);
        // or regenerate target Beast.
        mode = new Mode();
        mode.getEffects().add(new RegenerateTargetEffect());
        mode.getTargets().add(new TargetPermanent(filter));
        this.getSpellAbility().addMode(mode);
    }

    public VitalityCharm(final VitalityCharm card) {
        super(card);
    }

    @Override
    public VitalityCharm copy() {
        return new VitalityCharm(this);
    }
}
