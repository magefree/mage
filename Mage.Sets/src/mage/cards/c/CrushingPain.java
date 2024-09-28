package mage.cards.c;

import java.util.UUID;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.WasDealtDamageThisTurnPredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX
 */
public final class CrushingPain extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature that was dealt damage this turn");

    static {
        filter.add(WasDealtDamageThisTurnPredicate.instance);
    }

    public CrushingPain (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{R}");
        this.subtype.add(SubType.ARCANE);

        // Crushing Pain deals 6 damage to target creature that was dealt damage this turn.
        this.getSpellAbility().addEffect(new DamageTargetEffect(6));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(filter));
    }

    private CrushingPain(final CrushingPain card) {
        super(card);
    }

    @Override
    public CrushingPain copy() {
        return new CrushingPain(this);
    }

}


