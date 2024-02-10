package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.SourcePermanentToughnessValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.combat.CanAttackAsThoughItDidntHaveDefenderTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author DominionSpy
 */
public final class ThePrideOfHullClade extends CardImpl {

    public ThePrideOfHullClade(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{10}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.CROCODILE);
        this.subtype.add(SubType.ELK);
        this.subtype.add(SubType.TURTLE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(15);

        // This spell costs {X} less to cast, where X is the total toughness of creatures you control.
        this.addAbility(new SimpleStaticAbility(Zone.ALL,
                new SpellCostReductionSourceEffect(TotalToughnessOfControlledCreaturesValue.instance)));

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // {2}{U}{U}: Until end of turn, target creature you control gets +1/+0, gains "Whenever this creature deals combat damage to a player, draw cards equal to its toughness," and can attack as though it didn't have defender.
        Ability ability = new SimpleActivatedAbility(
                new BoostTargetEffect(1, 0)
                        .setText("Until end of turn, target creature you control gets +1/0"),
                new ManaCostsImpl<>("{2}{U}{U}"))
                ;
        ability.addEffect(
                new GainAbilityTargetEffect(
                        new DealsCombatDamageToAPlayerTriggeredAbility(
                                new DrawCardSourceControllerEffect
                                        (SourcePermanentToughnessValue.getInstance()), false))
                        .setText(", gains \"Whenever this creature deals combat damage to a player, draw cards equal to its toughness,\""));
        ability.addEffect(
                new CanAttackAsThoughItDidntHaveDefenderTargetEffect(Duration.EndOfTurn)
                        .setText("and can attack as though it didn't have defender."));
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);
    }

    private ThePrideOfHullClade(final ThePrideOfHullClade card) {
        super(card);
    }

    @Override
    public ThePrideOfHullClade copy() {
        return new ThePrideOfHullClade(this);
    }
}

enum TotalToughnessOfControlledCreaturesValue implements DynamicValue {
    instance;

    @Override
    public TotalToughnessOfControlledCreaturesValue copy() {
        return TotalToughnessOfControlledCreaturesValue.instance;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return game
                .getBattlefield()
                .getAllActivePermanents(
                        StaticFilters.FILTER_PERMANENT_CREATURE,
                        sourceAbility.getControllerId(), game)
                .stream()
                .map(Permanent::getToughness)
                .mapToInt(MageInt::getValue)
                .sum();
    }

    @Override
    public String getMessage() {
        return "the total toughness of creatures you control";
    }

    @Override
    public String toString() {
        return "X";
    }
}
