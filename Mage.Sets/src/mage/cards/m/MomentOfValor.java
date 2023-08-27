package mage.cards.m;

import mage.abilities.Mode;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author xenohedron
 */
public final class MomentOfValor extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature with power 4 or greater");

    static {
        filter.add(new PowerPredicate(ComparisonType.OR_GREATER, 4));
    }

    public MomentOfValor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{W}");

        // Choose one --
        // * Untap target creature. It gets +1/+0 and gains indestructible until end of turn.
        this.getSpellAbility().addEffect(new UntapTargetEffect());
        this.getSpellAbility().addEffect(new BoostTargetEffect(1, 0).setText("It gets +1/+0"));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(IndestructibleAbility.getInstance()).setText("and gains indestructible until end of turn"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

        // * Destroy target creature with power 4 or greater.
        Mode mode = new Mode(new DestroyTargetEffect());
        mode.addTarget(new TargetCreaturePermanent(filter));
        this.getSpellAbility().addMode(mode);
    }

    private MomentOfValor(final MomentOfValor card) {
        super(card);
    }

    @Override
    public MomentOfValor copy() {
        return new MomentOfValor(this);
    }
}
