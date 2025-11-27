package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.TransformIntoSourceTriggeredAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextUpkeepDelayedTriggeredAbility;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.*;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AnotherPredicate;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class ArchangelAvacyn extends TransformingDoubleFacedCard {

    private static final FilterCreaturePermanent nonAngelFilter = new FilterCreaturePermanent("a non-Angel creature you control");
    private static final FilterPermanent otherCreatureFilter = new FilterCreaturePermanent("other creature");

    static {
        otherCreatureFilter.add(AnotherPredicate.instance);
        nonAngelFilter.add(Predicates.not(SubType.ANGEL.getPredicate()));
        nonAngelFilter.add(TargetController.YOU.getControllerPredicate());
    }

    public ArchangelAvacyn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.ANGEL}, "{3}{W}{W}",
                "Avacyn, the Purifier",
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.ANGEL}, "R");

        this.getLeftHalfCard().setPT(4, 4);
        this.getRightHalfCard().setPT(6, 5);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Flying
        this.getLeftHalfCard().addAbility(FlyingAbility.getInstance());

        // Vigilance
        this.getLeftHalfCard().addAbility(VigilanceAbility.getInstance());

        // When Archangel Avacyn enters the battlefield, creatures you control gain indestructible until end of turn.
        this.getLeftHalfCard().addAbility(new EntersBattlefieldTriggeredAbility(new GainAbilityControlledEffect(
                IndestructibleAbility.getInstance(), Duration.EndOfTurn,
                StaticFilters.FILTER_PERMANENT_CREATURES
        ), false));

        // When a non-Angel creature you control dies, transform Archangel Avacyn at the beginning of the next upkeep.
        this.getLeftHalfCard().addAbility(new DiesCreatureTriggeredAbility(
                new CreateDelayedTriggeredAbilityEffect(
                        new AtTheBeginOfNextUpkeepDelayedTriggeredAbility(new TransformSourceEffect())
                ).setText("transform {this} at the beginning of the next upkeep"), false, nonAngelFilter
        ).setTriggerPhrase("When a non-Angel creature you control dies, "));

        // Avacyn, the Purifier

        // Flying
        this.getRightHalfCard().addAbility(FlyingAbility.getInstance());

        // When this creature transforms into Avacyn, the Purifier, it deals 3 damage to each other creature and each opponent.
        Ability ability = new TransformIntoSourceTriggeredAbility(
                new DamageAllEffect(3, "it", otherCreatureFilter)
        );
        ability.addEffect(new DamagePlayersEffect(3, TargetController.OPPONENT).setText("and each opponent"));
        this.getRightHalfCard().addAbility(ability);
    }

    private ArchangelAvacyn(final ArchangelAvacyn card) {
        super(card);
    }

    @Override
    public ArchangelAvacyn copy() {
        return new ArchangelAvacyn(this);
    }
}
