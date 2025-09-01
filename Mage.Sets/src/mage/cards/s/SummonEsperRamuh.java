package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.SagaAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SagaChapter;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SummonEsperRamuh extends CardImpl {

    private static final FilterCard filter = new FilterCard("noncreature, nonland cards");
    private static final FilterCreaturePermanent filter2 = new FilterCreaturePermanent(SubType.WIZARD, "Wizards");

    static {
        filter.add(Predicates.not(CardType.CREATURE.getPredicate()));
        filter.add(Predicates.not(CardType.LAND.getPredicate()));
    }

    private static final DynamicValue xValue = new CardsInControllerGraveyardCount(filter);

    public SummonEsperRamuh(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{2}{R}{R}");

        this.subtype.add(SubType.SAGA);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after III.)
        SagaAbility sagaAbility = new SagaAbility(this);

        // I -- Judgment Bolt -- This creature deals damage equal to the number of noncreature, nonland cards in your graveyard to target creature an opponent controls.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_I, ability -> {
            ability.addEffect(new DamageTargetEffect(xValue)
                    .setText("{this} deals damage equal to the number of noncreature, nonland " +
                            "cards in your graveyard to target creature an opponent controls"));
            ability.addTarget(new TargetOpponentsCreaturePermanent());
            ability.withFlavorWord("Judgment Bolt");
        });

        // II, III -- Wizards you control get +1/+0 until end of turn.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_II, SagaChapter.CHAPTER_III,
                new BoostControlledEffect(1, 0, Duration.EndOfTurn, filter2)
        );
        this.addAbility(sagaAbility);
    }

    private SummonEsperRamuh(final SummonEsperRamuh card) {
        super(card);
    }

    @Override
    public SummonEsperRamuh copy() {
        return new SummonEsperRamuh(this);
    }
}
