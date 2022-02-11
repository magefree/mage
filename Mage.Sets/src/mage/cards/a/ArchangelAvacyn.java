package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextUpkeepDelayedTriggeredAbility;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class ArchangelAvacyn extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("a non-Angel creature you control");

    static {
        filter.add(Predicates.not(SubType.ANGEL.getPredicate()));
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public ArchangelAvacyn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}{W}");

        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.ANGEL);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        this.secondSideCardClazz = AvacynThePurifier.class;

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // When Archangel Avacyn enters the battlefield, creatures you control gain indestructible until end of turn.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GainAbilityControlledEffect(
                IndestructibleAbility.getInstance(), Duration.EndOfTurn,
                StaticFilters.FILTER_PERMANENT_CREATURES
        ), false));

        // When a non-Angel creature you control dies, transform Archangel Avacyn at the beginning of the next upkeep.
        this.addAbility(new TransformAbility());
        this.addAbility(new DiesCreatureTriggeredAbility(
                new CreateDelayedTriggeredAbilityEffect(
                        new AtTheBeginOfNextUpkeepDelayedTriggeredAbility(new TransformSourceEffect())
                ).setText("transform {this} at the beginning of the next upkeep"), false, filter
        ).setTriggerPhrase("When a non-Angel creature you control dies, "));
    }

    private ArchangelAvacyn(final ArchangelAvacyn card) {
        super(card);
    }

    @Override
    public ArchangelAvacyn copy() {
        return new ArchangelAvacyn(this);
    }
}
