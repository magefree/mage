
package mage.cards.k;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextUpkeepDelayedTriggeredAbility;
import mage.abilities.condition.common.AttachedToMatchesFilterCondition;
import mage.abilities.costs.common.TapAttachedCost;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.counter.AddCountersAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author L_J
 */
public final class KrovikanPlague extends CardImpl {
    
    private static final FilterControlledCreaturePermanent filterNonWall = new FilterControlledCreaturePermanent("non-Wall creature you control");

    static {
        filterNonWall.add(Predicates.not(SubType.WALL.getPredicate()));
    }

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("enchanted creature is untapped");
    
    static {
        filter.add(TappedPredicate.UNTAPPED);
    }

    public KrovikanPlague(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}");
        this.subtype.add(SubType.AURA);

        // Enchant non-Wall creature you control
        TargetPermanent auraTarget = new TargetControlledCreaturePermanent(filterNonWall);
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // When Krovikan Plague enters the battlefield, draw a card at the beginning of the next turn's upkeep.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateDelayedTriggeredAbilityEffect(
                new AtTheBeginOfNextUpkeepDelayedTriggeredAbility(new DrawCardSourceControllerEffect(1), Duration.OneUse)), false));

        // Tap enchanted creature: Tap enchanted creature: Krovikan Plague deals 1 damage to any target. Put a -0/-1 counter on enchanted creature. Activate this ability only if enchanted creature is untapped.
        Ability ability2 = new ActivateIfConditionActivatedAbility(Zone.BATTLEFIELD,
                new DamageTargetEffect(1), new TapAttachedCost(), new AttachedToMatchesFilterCondition(filter));
        ability2.addEffect(new AddCountersAttachedEffect(CounterType.M0M1.createInstance(),"enchanted creature"));
        ability2.addTarget(new TargetAnyTarget());
        this.addAbility(ability2);

    }

    private KrovikanPlague(final KrovikanPlague card) {
        super(card);
    }

    @Override
    public KrovikanPlague copy() {
        return new KrovikanPlague(this);
    }
}
