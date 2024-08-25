package mage.cards.f;

import mage.ObjectColor;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.target.TargetSpell;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FlareOfDenial extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("a nontoken blue creature");

    static {
        filter.add(TokenPredicate.FALSE);
        filter.add(new ColorPredicate(ObjectColor.BLUE));
    }

    public FlareOfDenial(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}{U}");

        // You may sacrifice a nontoken blue creature rather than pay this spell's mana cost.
        this.addAbility(new AlternativeCostSourceAbility(new SacrificeTargetCost(filter)));

        // Counter target spell.
        this.getSpellAbility().addEffect(new CounterTargetEffect());
        this.getSpellAbility().addTarget(new TargetSpell());
    }

    private FlareOfDenial(final FlareOfDenial card) {
        super(card);
    }

    @Override
    public FlareOfDenial copy() {
        return new FlareOfDenial(this);
    }
}
