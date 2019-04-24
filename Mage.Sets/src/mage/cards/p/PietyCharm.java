
package mage.cards.p;

import java.util.UUID;
import mage.abilities.Mode;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.AttachedToPredicate;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LoneFox
 */
public final class PietyCharm extends CardImpl {

    private static final FilterPermanent filter1 = new FilterPermanent("Aura attached to a creature");
    private static final FilterCreaturePermanent filter2 = new FilterCreaturePermanent("Soldier creature");

    static {
        filter1.add(new SubtypePredicate(SubType.AURA));
        filter1.add(new AttachedToPredicate(new FilterCreaturePermanent()));
        filter2.add(new SubtypePredicate(SubType.SOLDIER));
    }

    public PietyCharm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{W}");

        // Choose one - Destroy target Aura attached to a creature
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(filter1));
        // or target Soldier creature gets +2/+2 until end of turn
        Mode mode = new Mode();
        mode.getEffects().add(new BoostTargetEffect(2, 2, Duration.EndOfTurn));
        mode.getTargets().add(new TargetCreaturePermanent(filter2));
        this.getSpellAbility().addMode(mode);
        // or creatures you control gain vigilance until end of turn.
        mode = new Mode();
        mode.getEffects().add(new GainAbilityAllEffect(VigilanceAbility.getInstance(), Duration.EndOfTurn, new FilterControlledCreaturePermanent("creatures you control")));
        this.getSpellAbility().addMode(mode);
    }

    public PietyCharm(final PietyCharm card) {
        super(card);
    }

    @Override
    public PietyCharm copy() {
        return new PietyCharm(this);
    }
}
