package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.DayboundAbility;
import mage.abilities.keyword.HexproofAbility;
import mage.abilities.keyword.NightboundAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AvabruckCaretaker extends TransformingDoubleFacedCard {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("another target creature you control");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public AvabruckCaretaker(UUID ownerId, CardSetInfo setInfo) {
        super(
                ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.WEREWOLF}, "{4}{G}{G}",
                "Hollowhenge Huntmaster",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.WEREWOLF}, "G"
        );
        this.getLeftHalfCard().setPT(4, 4);
        this.getRightHalfCard().setPT(6, 6);

        // Hexproof
        this.getLeftHalfCard().addAbility(HexproofAbility.getInstance());

        // At the beginning of combat on your turn, put two +1/+1 counters on another target creature you control.
        Ability ability = new BeginningOfCombatTriggeredAbility(
                new AddCountersTargetEffect(
                        CounterType.P1P1.createInstance(2)
                ), TargetController.YOU, false
        );
        ability.addTarget(new TargetPermanent(filter));
        this.getLeftHalfCard().addAbility(ability);

        // Daybound
        this.getLeftHalfCard().addAbility(new DayboundAbility());

        // Hollowhenge Huntmaster
        // Hexproof
        this.getRightHalfCard().addAbility(HexproofAbility.getInstance());

        // Other permanents you control have hexproof.
        this.getRightHalfCard().addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                HexproofAbility.getInstance(), Duration.WhileOnBattlefield,
                StaticFilters.FILTER_PERMANENTS, true
        )));

        // At the beginning of combat on your turn, put two +1/+1 counters on each creature you control.
        this.getRightHalfCard().addAbility(new BeginningOfCombatTriggeredAbility(
                new AddCountersAllEffect(
                        CounterType.P1P1.createInstance(2),
                        StaticFilters.FILTER_CONTROLLED_CREATURE
                ), TargetController.YOU, false
        ));

        // Nightbound
        this.getRightHalfCard().addAbility(new NightboundAbility());
    }

    private AvabruckCaretaker(final AvabruckCaretaker card) {
        super(card);
    }

    @Override
    public AvabruckCaretaker copy() {
        return new AvabruckCaretaker(this);
    }
}
