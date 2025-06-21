package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ControlsPermanentsControllerTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.IsStepCondition;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DontUntapInControllersUntapStepSourceEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.effects.common.UntapSourceEffect;
import mage.abilities.effects.common.combat.CantAttackUnlessDefenderControllsPermanent;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterAttackingCreature;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
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
        this.addAbility(new SimpleStaticAbility(new DontUntapInControllersUntapStepSourceEffect()));

        // {U}{U}, Sacrifice a creature: Untap Marjhan. Activate this ability only during your upkeep.
        Ability ability = new ActivateIfConditionActivatedAbility(
                new UntapSourceEffect(), new ManaCostsImpl<>("{U}{U}"), IsStepCondition.getMyUpkeep()
        );
        ability.addCost(new SacrificeTargetCost(StaticFilters.FILTER_PERMANENT_CREATURE));
        this.addAbility(ability);

        // Marjhan can't attack unless defending player controls an Island.
        this.addAbility(new SimpleStaticAbility(new CantAttackUnlessDefenderControllsPermanent(new FilterLandPermanent(SubType.ISLAND, "an Island"))));

        // {U}{U}: Marjhan gets -1/-0 until end of turn and deals 1 damage to target attacking creature without flying.
        ability = new SimpleActivatedAbility(new BoostSourceEffect(-1, 0, Duration.EndOfTurn), new ManaCostsImpl<>("{U}{U}"));
        ability.addEffect(new DamageTargetEffect(1, "and"));
        ability.addTarget(new TargetPermanent(filter));
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
