package mage.cards.m;

import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.common.continuous.BecomesColorSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.effects.common.counter.DoubleCounterOnEachPermanentEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.CardSetInfo;
import mage.cards.ModalDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MilesMorales extends ModalDoubleFacedCard {

    private static final FilterPermanent filter = new FilterControlledPermanent("Spider and legendary creature you control");

    static {
        filter.add(Predicates.or(
                SubType.SPIDER.getPredicate(),
                Predicates.and(
                        SuperType.LEGENDARY.getPredicate(),
                        CardType.CREATURE.getPredicate()
                )));
    }

    public MilesMorales(UUID ownerId, CardSetInfo setInfo) {
        super(
                ownerId, setInfo,
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.CITIZEN, SubType.HERO}, "{1}{G}",
                "Ultimate Spider-Man",
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.SPIDER, SubType.HUMAN, SubType.HERO}, "{3}{R}{G}{W}"
        );
        this.getLeftHalfCard().setPT(1, 2);
        this.getRightHalfCard().setPT(4, 3);

        // When Miles Morales enters, put a +1/+1 counter on each of up to two target creatures.
        Ability ability = new EntersBattlefieldTriggeredAbility(new AddCountersTargetEffect(CounterType.P1P1.createInstance()));
        ability.addTarget(new TargetCreaturePermanent(0, 2));
        this.addAbility(ability);

        // {3}{R}{G}{W}: Transform Miles Morales. Activate only as a sorcery.
        this.getLeftHalfCard().addAbility(new ActivateAsSorceryActivatedAbility(
                new TransformSourceEffect(), new ManaCostsImpl<>("{3}{R}{G}{W}")
        ));

        // Ultimate Spider-Man
        // First strike
        this.getRightHalfCard().addAbility(FirstStrikeAbility.getInstance());

        // Haste
        this.getRightHalfCard().addAbility(HasteAbility.getInstance());

        // Camouflage -- {2}: Put a +1/+1 counter on Ultimate Spider-Man. He gains hexproof and becomes colorless until end of turn.
        ability = new SimpleActivatedAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()), new GenericManaCost(2)
        );
        ability.addEffect(new GainAbilitySourceEffect(
                HexproofAbility.getInstance(), Duration.EndOfTurn
        ).setText("He gains hexproof"));
        ability.addEffect(new BecomesColorSourceEffect(
                ObjectColor.COLORLESS, Duration.EndOfTurn
        ).setText("and becomes colorless until end of turn"));
        this.getRightHalfCard().addAbility(ability.withFlavorWord("Camouflage"));

        // Whenever you attack, double the number of each kind of counter on each Spider and legendary creature you control.
        this.getRightHalfCard().addAbility(new AttacksWithCreaturesTriggeredAbility(
                new DoubleCounterOnEachPermanentEffect(null, filter), 1
        ));
    }

    private MilesMorales(final MilesMorales card) {
        super(card);
    }

    @Override
    public MilesMorales copy() {
        return new MilesMorales(this);
    }
}
