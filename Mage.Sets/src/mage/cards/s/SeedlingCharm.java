
package mage.cards.s;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Mode;
import mage.abilities.effects.common.RegenerateTargetEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.filter.predicate.permanent.AttachedToPredicate;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LoneFox
 */
public final class SeedlingCharm extends CardImpl {

    private static final FilterPermanent filter1 = new FilterPermanent("Aura attached to a creature");
    private static final FilterCreaturePermanent filter2 = new FilterCreaturePermanent("green creature");

    static {
        filter1.add(SubType.AURA.getPredicate());
        filter1.add(new AttachedToPredicate(new FilterCreaturePermanent()));
        filter2.add(new ColorPredicate(ObjectColor.GREEN));
    }

    public SeedlingCharm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{G}");

        // Choose one - Return target Aura attached to a creature to its owner's hand
        this.getSpellAbility().addEffect(new ReturnToHandTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(filter1));
        // or regenerate target green creature
        Mode mode = new Mode(new RegenerateTargetEffect());
        mode.addTarget(new TargetPermanent(filter2));
        this.getSpellAbility().addMode(mode);
        // or target creature gains trample until end of turn.
        mode = new Mode(new GainAbilityTargetEffect(TrampleAbility.getInstance(), Duration.EndOfTurn));
        mode.addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addMode(mode);
    }

    private SeedlingCharm(final SeedlingCharm card) {
        super(card);
    }

    @Override
    public SeedlingCharm copy() {
        return new SeedlingCharm(this);
    }
}
