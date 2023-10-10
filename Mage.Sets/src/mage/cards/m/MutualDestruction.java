package mage.cards.m;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MutualDestruction extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent();
    private static final FilterPermanent filter2 = new FilterCreaturePermanent("creature (to destoy)");

    static {
        filter.add(new AbilityPredicate(FlashAbility.class));
    }

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(filter);

    public MutualDestruction(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{B}");

        // This spell has flash as long as you control a permanent with flash.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(FlashAbility.getInstance(), Duration.EndOfGame, true),
                condition, "This spell has flash as long as you control a permanent with flash."
        )).setRuleAtTheTop(true));

        // As an additional cost to cast this spell, sacrifice a creature.
        this.getSpellAbility().addCost(new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT));

        // Destroy target creature.
        this.getSpellAbility().addEffect(new DestroyTargetEffect("Destroy target creature"));
        this.getSpellAbility().addTarget(new TargetPermanent(filter2));
    }

    private MutualDestruction(final MutualDestruction card) {
        super(card);
    }

    @Override
    public MutualDestruction copy() {
        return new MutualDestruction(this);
    }
}
