package mage.cards.f;

import mage.ObjectColor;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.CopyTargetStackObjectEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.target.TargetSpell;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class FlareOfDuplication extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent("nontoken red creature");

    static {
        filter.add(TokenPredicate.FALSE);
        filter.add(new ColorPredicate(ObjectColor.RED));
    }

    public FlareOfDuplication(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{R}{R}");

        // You may sacrifice a nontoken red creature rather than pay this spell's mana cost.
        this.addAbility(new AlternativeCostSourceAbility(new SacrificeTargetCost(filter)).setRuleAtTheTop(true));

        // Copy target instant or sorcery spell. You may choose new targets for the copy.
        this.getSpellAbility().addEffect(new CopyTargetStackObjectEffect());
        this.getSpellAbility().addTarget(new TargetSpell(StaticFilters.FILTER_SPELL_INSTANT_OR_SORCERY));
    }

    private FlareOfDuplication(final FlareOfDuplication card) {
        super(card);
    }

    @Override
    public FlareOfDuplication copy() {
        return new FlareOfDuplication(this);
    }
}
