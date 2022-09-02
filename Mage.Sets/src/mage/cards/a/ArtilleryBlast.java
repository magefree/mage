package mage.cards.a;

import java.util.UUID;

import mage.abilities.dynamicvalue.IntPlusDynamicValue;
import mage.abilities.dynamicvalue.common.DomainValue;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.hint.common.DomainHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author weirddan455
 */
public final class ArtilleryBlast extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("tapped creature");

    static {
        filter.add(TappedPredicate.TAPPED);
    }

    public ArtilleryBlast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}");

        // Domain--Artillery Blast deals X damage to target tapped creature, where X is 1 plus the number of basic land types among lands you control.
        this.getSpellAbility().addEffect(new DamageTargetEffect(new IntPlusDynamicValue(1, DomainValue.REGULAR))
                .setText("{this} deals X damage to target tapped creature, where X is 1 plus the number of basic land types among lands you control"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(filter));
        this.getSpellAbility().setAbilityWord(AbilityWord.DOMAIN);
        this.getSpellAbility().addHint(DomainHint.instance);
    }

    private ArtilleryBlast(final ArtilleryBlast card) {
        super(card);
    }

    @Override
    public ArtilleryBlast copy() {
        return new ArtilleryBlast(this);
    }
}
