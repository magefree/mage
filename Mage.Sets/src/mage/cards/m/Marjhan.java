
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ControlsPermanentsControllerTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.IsStepCondition;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DontUntapInControllersUntapStepSourceEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.effects.common.UntapSourceEffect;
import mage.abilities.effects.common.combat.CantAttackUnlessDefenderControllsPermanent;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.filter.common.FilterAttackingCreature;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class Marjhan extends CardImpl {

    private static final FilterAttackingCreature filter = new FilterAttackingCreature("attacking creature without flying");

    static {
        filter.add(Predicates.not(new AbilityPredicate(FlyingAbility.class)));
    }

    public Marjhan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{U}{U}");
        this.subtype.add(SubType.SERPENT);
        this.power = new MageInt(8);
        this.toughness = new MageInt(8);

        // Marjhan doesn't untap during your untap step.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new DontUntapInControllersUntapStepSourceEffect()));

        // {U}{U}, Sacrifice a creature: Untap Marjhan. Activate this ability only during your upkeep.
        Ability ability = new ConditionalActivatedAbility(Zone.BATTLEFIELD,
                new UntapSourceEffect(), new ManaCostsImpl<>("{U}{U}"), new IsStepCondition(PhaseStep.UPKEEP), null);
        ability.addCost(new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT));
        this.addAbility(ability);

        // Marjhan can't attack unless defending player controls an Island.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CantAttackUnlessDefenderControllsPermanent(new FilterLandPermanent(SubType.ISLAND, "an Island"))));

        // {U}{U}: Marjhan gets -1/-0 until end of turn and deals 1 damage to target attacking creature without flying.
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostSourceEffect(-1, 0, Duration.EndOfTurn), new ManaCostsImpl<>("{U}{U}"));
        ability.addEffect(new DamageTargetEffect(1, "and"));
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability);

        // When you control no Islands, sacrifice Marjhan.
        this.addAbility(new ControlsPermanentsControllerTriggeredAbility(
                new FilterLandPermanent(SubType.ISLAND, "no Islands"), ComparisonType.EQUAL_TO, 0,
                new SacrificeSourceEffect()));
    }

    private Marjhan(final Marjhan card) {
        super(card);
    }

    @Override
    public Marjhan copy() {
        return new Marjhan(this);
    }
}
