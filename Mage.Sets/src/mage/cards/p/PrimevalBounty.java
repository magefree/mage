
package mage.cards.p;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.FilterSpell;
import mage.filter.StaticFilters;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.Predicates;
import mage.game.permanent.token.BeastToken;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class PrimevalBounty extends CardImpl {

    private static final FilterSpell filterNonCreature = new FilterSpell("a noncreature spell");

    static {
        filterNonCreature.add(Predicates.not(CardType.CREATURE.getPredicate()));
    }

    public PrimevalBounty(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{5}{G}");

        // Whenever you cast a creature spell, create a 3/3 green Beast creature token.
        this.addAbility(new SpellCastControllerTriggeredAbility(new CreateTokenEffect(new BeastToken()), StaticFilters.FILTER_SPELL_A_CREATURE, false));

        // Whenever you cast a noncreature spell, put three +1/+1 counters on target creature you control.
        Effect effect = new AddCountersTargetEffect(CounterType.P1P1.createInstance(3));
        Ability ability = new SpellCastControllerTriggeredAbility(effect, filterNonCreature, false);
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);

        // Whenever a land enters the battlefield under your control, you gain 3 life.
        effect = new GainLifeEffect(3);
        ability = new EntersBattlefieldControlledTriggeredAbility(effect, StaticFilters.FILTER_LAND_A);
        this.addAbility(ability);

    }

    private PrimevalBounty(final PrimevalBounty card) {
        super(card);
    }

    @Override
    public PrimevalBounty copy() {
        return new PrimevalBounty(this);
    }
}
