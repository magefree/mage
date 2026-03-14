package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.LeavesBattlefieldAllTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextUpkeepDelayedTriggeredAbility;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.keyword.EarthbendTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.target.common.TargetControlledLandPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AangAtTheCrossroads extends TransformingDoubleFacedCard {

    private static final FilterCard filter = new FilterCreatureCard("creature card with mana value 4 or less");
    private static final FilterPermanent saviorFilter = new FilterPermanent("land creatures");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 5));
        saviorFilter.add(CardType.LAND.getPredicate());
        saviorFilter.add(CardType.CREATURE.getPredicate());
    }

    public AangAtTheCrossroads(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.AVATAR, SubType.ALLY}, "{2}{G}{W}{U}",
                "Aang, Destined Savior",
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.AVATAR, SubType.ALLY}, "");

        this.getLeftHalfCard().setPT(3, 3);
        this.getRightHalfCard().setPT(4, 4);

        // Flying
        this.getLeftHalfCard().addAbility(FlyingAbility.getInstance());

        // When Aang enters, look at the top five cards of your library. You may put a creature card with mana value 4 or less from among them onto the battlefield. Put the rest on the bottom of your library in a random order.
        this.getLeftHalfCard().addAbility(new EntersBattlefieldTriggeredAbility(new LookLibraryAndPickControllerEffect(
                5, 1, filter, PutCards.BATTLEFIELD, PutCards.BOTTOM_RANDOM
        )));

        // When another creature you control leaves the battlefield, transform Aang at the beginning of the next upkeep.
        this.getLeftHalfCard().addAbility(new LeavesBattlefieldAllTriggeredAbility(new CreateDelayedTriggeredAbilityEffect(
                new AtTheBeginOfNextUpkeepDelayedTriggeredAbility(new TransformSourceEffect())
        ).setText("transform {this} at the beginning of the next upkeep"), StaticFilters.FILTER_ANOTHER_CREATURE_YOU_CONTROL)
                .setTriggerPhrase("When another creature you control leaves the battlefield, "));

        // Aang, Destined Savior

        // Flying
        this.getRightHalfCard().addAbility(FlyingAbility.getInstance());

        // Land creatures you control have vigilance.
        this.getRightHalfCard().addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                VigilanceAbility.getInstance(), Duration.WhileOnBattlefield, saviorFilter
        )));

        // At the beginning of combat on your turn, earthbend 2.
        Ability ability = new BeginningOfCombatTriggeredAbility(new EarthbendTargetEffect(2));
        ability.addTarget(new TargetControlledLandPermanent());
        this.getRightHalfCard().addAbility(ability);
    }

    private AangAtTheCrossroads(final AangAtTheCrossroads card) {
        super(card);
    }

    @Override
    public AangAtTheCrossroads copy() {
        return new AangAtTheCrossroads(this);
    }
}
