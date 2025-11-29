package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledSpellsEffect;
import mage.abilities.keyword.ConvokeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.PersistAbility;
import mage.abilities.triggers.BeginningOfFirstMainTriggeredAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterNonlandCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EirduCarrierOfDawn extends TransformingDoubleFacedCard {

    private static final FilterNonlandCard filter = new FilterNonlandCard("creature spells you cast");

    static {
        filter.add(CardType.CREATURE.getPredicate());
        filter.add(Predicates.not(new AbilityPredicate(ConvokeAbility.class)));
    }

    public EirduCarrierOfDawn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.ELEMENTAL, SubType.GOD}, "{3}{W}{W}",
                "Isilu, Carrier of Twilight",
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.ELEMENTAL, SubType.GOD}, "B"
        );

        // Eirdu, Carrier of Dawn
        this.getLeftHalfCard().setPT(5, 5);

        // Flying
        this.getLeftHalfCard().addAbility(FlyingAbility.getInstance());

        // Lifelink
        this.getLeftHalfCard().addAbility(LifelinkAbility.getInstance());

        // Creature spells you cast have convoke.
        this.getLeftHalfCard().addAbility(new SimpleStaticAbility(new GainAbilityControlledSpellsEffect(new ConvokeAbility(), filter)));

        // At the beginning of your first main phase, you may pay {B}. If you do, transform Eirdu.
        Ability ability = new BeginningOfFirstMainTriggeredAbility(new DoIfCostPaid(new TransformSourceEffect(), new ManaCostsImpl<>("{B}")));
        this.getLeftHalfCard().addAbility(ability);

        // Isilu, Carrier of Twilight
        this.getRightHalfCard().setPT(5, 5);

        // Flying
        this.getRightHalfCard().addAbility(FlyingAbility.getInstance());

        // Lifelink
        this.getRightHalfCard().addAbility(LifelinkAbility.getInstance());

        // Each other nontoken creature you control has persist.
        this.getRightHalfCard().addAbility(new SimpleStaticAbility(new GainAbilityAllEffect(
                new PersistAbility(), Duration.WhileControlled,
                StaticFilters.FILTER_CONTROLLED_CREATURE_NON_TOKEN, true
        ).setText("each other nontoken creature you control has persist")));

        // At the beginning of your first main phase, you may pay {W}. If you do, transform Isilu.
        this.getRightHalfCard().addAbility(new BeginningOfFirstMainTriggeredAbility(new DoIfCostPaid(new TransformSourceEffect(), new ManaCostsImpl<>("{W}"))));
    }

    private EirduCarrierOfDawn(final EirduCarrierOfDawn card) {
        super(card);
    }

    @Override
    public EirduCarrierOfDawn copy() {
        return new EirduCarrierOfDawn(this);
    }
}
