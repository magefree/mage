package mage.cards.s;

import mage.abilities.Mode;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SarkhansResolve extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("creature with flying");

    static {
        filter.add(new AbilityPredicate(FlyingAbility.class));
    }

    public SarkhansResolve(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{G}");

        // Choose one --
        // * Target creature gets +3/+3 until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(3, 3));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

        // * Destroy target creature with flying.
        this.getSpellAbility().addMode(new Mode(new DestroyTargetEffect()).addTarget(new TargetPermanent(filter)));
    }

    private SarkhansResolve(final SarkhansResolve card) {
        super(card);
    }

    @Override
    public SarkhansResolve copy() {
        return new SarkhansResolve(this);
    }
}
