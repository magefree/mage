package mage.cards.t;

import mage.abilities.common.SagaAbility;
import mage.abilities.condition.common.FerociousCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.hint.common.FerociousHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SagaChapter;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.game.permanent.token.GoldToken;
import mage.game.permanent.token.HumanSoldierToken;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheFirstIroanGames extends CardImpl {

    public TheFirstIroanGames(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}");

        this.subtype.add(SubType.SAGA);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after IV.)
        SagaAbility sagaAbility = new SagaAbility(this, SagaChapter.CHAPTER_IV);

        // I — Create a 1/1 white Human Soldier token.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_I, new CreateTokenEffect(new HumanSoldierToken())
        );

        // II — Put three +1/+1 counters on target creature you control.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_II, SagaChapter.CHAPTER_II,
                new AddCountersTargetEffect(CounterType.P1P1.createInstance(3)),
                new TargetControlledCreaturePermanent()
        );

        // III — If you control a creature with power 4 or greater, draw two cards.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_III, new ConditionalOneShotEffect(
                        new DrawCardSourceControllerEffect(2), FerociousCondition.instance,
                        "if you control a creature with power 4 or greater, draw two cards"
                )
        );
        sagaAbility.addHint(FerociousHint.instance);

        // IV — Create a Gold token.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_IV, new CreateTokenEffect(new GoldToken()));

        this.addAbility(sagaAbility);
    }

    private TheFirstIroanGames(final TheFirstIroanGames card) {
        super(card);
    }

    @Override
    public TheFirstIroanGames copy() {
        return new TheFirstIroanGames(this);
    }
}
