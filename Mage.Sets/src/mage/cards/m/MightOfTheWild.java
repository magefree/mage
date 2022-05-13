
package mage.cards.m;

import java.util.UUID;
import mage.abilities.Mode;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.combat.CantBlockAllEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Duration;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.target.TargetPermanent;

/**
 *
 * @author Styxo
 */
public final class MightOfTheWild extends CardImpl {

    private static final FilterCreaturePermanent filterMode1 = new FilterCreaturePermanent("Creatures with power 3 or less");
    private static final FilterPermanent filterMode2 = new FilterPermanent("artifact or enchantment");

    static {
        filterMode1.add(new PowerPredicate(ComparisonType.FEWER_THAN, 4));
        filterMode2.add(Predicates.or(CardType.ARTIFACT.getPredicate(), CardType.ENCHANTMENT.getPredicate()));
    }

    public MightOfTheWild(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{R}{G}{W}");

        // Choose one - Creatures with power 3 or less can't block this turn.
        this.getSpellAbility().addEffect(new CantBlockAllEffect(filterMode1, Duration.EndOfTurn));

        // Destroy target artifact or enchantment.
        Mode mode = new Mode(new DestroyTargetEffect());
        mode.addTarget(new TargetPermanent(filterMode2));
        this.getSpellAbility().addMode(mode);

        // Creatures you control gain indestructible this turn.
        mode = new Mode(new GainAbilityControlledEffect(IndestructibleAbility.getInstance(), Duration.EndOfTurn, StaticFilters.FILTER_CONTROLLED_CREATURES));
        this.getSpellAbility().addMode(mode);
    }

    private MightOfTheWild(final MightOfTheWild card) {
        super(card);
    }

    @Override
    public MightOfTheWild copy() {
        return new MightOfTheWild(this);
    }
}
