package mage.cards.s;

import mage.abilities.Mode;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.target.TargetPermanent;
import mage.target.common.TargetArtifactPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SmashToDust extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("creature with defender");

    static {
        filter.add(new AbilityPredicate(DefenderAbility.class));
    }

    public SmashToDust(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{R}");

        // Choose one --
        // * Destroy target artifact.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetArtifactPermanent());

        // * Destroy target creature with defender.
        this.getSpellAbility().addMode(new Mode(new DestroyTargetEffect()).addTarget(new TargetPermanent(filter)));

        // * Smash to Dust deals 1 damage to each creature your opponents control.
        this.getSpellAbility().addMode(new Mode(new DamageAllEffect(
                1, StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURE
        ).setText("{this} deals 1 damage to each creature your opponents control")));
    }

    private SmashToDust(final SmashToDust card) {
        super(card);
    }

    @Override
    public SmashToDust copy() {
        return new SmashToDust(this);
    }
}
