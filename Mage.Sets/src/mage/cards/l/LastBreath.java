package mage.cards.l;

import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.GainLifeTargetControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class LastBreath extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature with power 2 or less");

    static {
        filter.add(new PowerPredicate(ComparisonType.FEWER_THAN, 3));
    }

    public LastBreath(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}");

        // Exile target creature with power 2 or less. Its controller gains 4 life.
        this.getSpellAbility().addEffect(new ExileTargetEffect());
        this.getSpellAbility().addEffect(new GainLifeTargetControllerEffect(4));
        this.getSpellAbility().addTarget(new TargetPermanent(filter));

    }

    private LastBreath(final LastBreath card) {
        super(card);
    }

    @Override
    public LastBreath copy() {
        return new LastBreath(this);
    }
}
