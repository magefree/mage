package mage.cards.p;

import java.util.UUID;

import mage.abilities.Mode;
import mage.abilities.condition.common.BlightedCondition;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.BlightAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.target.TargetPermanent;

/**
 *
 * @author muz
 */
public final class PyrrhicStrike extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("creature with mana value 3 or greater");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.OR_GREATER, 3));
    }

    public PyrrhicStrike(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{W}");

        // As an additional cost to cast this spell, you may blight 2.
        this.addAbility(new BlightAbility(2));

        // Choose one. If this spell's additional cost was paid, choose both instead.
        this.getSpellAbility().getModes().setChooseText(
            "Choose one. If this spell's additional cost was paid, choose both instead."
        );
        this.getSpellAbility().getModes().setMoreCondition(2, BlightedCondition.instance);

        // * Destroy target artifact or enchantment.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_ENCHANTMENT));

        // * Destroy target creature with mana value 3 or greater.
        Mode mode = new Mode(new DestroyTargetEffect());
        mode.addTarget(new TargetPermanent(filter));
        this.getSpellAbility().addMode(mode);
    }

    private PyrrhicStrike(final PyrrhicStrike card) {
        super(card);
    }

    @Override
    public PyrrhicStrike copy() {
        return new PyrrhicStrike(this);
    }
}
