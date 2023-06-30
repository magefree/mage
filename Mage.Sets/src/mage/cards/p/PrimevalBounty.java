package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.common.LandfallAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.FilterSpell;
import mage.filter.StaticFilters;
import mage.filter.predicate.Predicates;
import mage.game.permanent.token.BeastToken;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
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
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new CreateTokenEffect(new BeastToken()), StaticFilters.FILTER_SPELL_A_CREATURE, false
        ));

        // Whenever you cast a noncreature spell, put three +1/+1 counters on target creature you control.
        Ability ability = new SpellCastControllerTriggeredAbility(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance(3)),
                filterNonCreature, false
        );
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);

        // Whenever a land enters the battlefield under your control, you gain 3 life.
        this.addAbility(new LandfallAbility(new GainLifeEffect(3)));
    }

    private PrimevalBounty(final PrimevalBounty card) {
        super(card);
    }

    @Override
    public PrimevalBounty copy() {
        return new PrimevalBounty(this);
    }
}
