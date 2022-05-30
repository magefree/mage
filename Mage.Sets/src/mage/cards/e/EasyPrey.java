package mage.cards.e;

import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EasyPrey extends CardImpl {

    private static final FilterPermanent filter
            = new FilterCreaturePermanent("creature with mana value 2 or less");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 3));
    }

    public EasyPrey(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{B}");

        // Destroy target creature with converted mana cost 2 or less.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(filter));

        // Cycling {2}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{2}")));
    }

    private EasyPrey(final EasyPrey card) {
        super(card);
    }

    @Override
    public EasyPrey copy() {
        return new EasyPrey(this);
    }
}
