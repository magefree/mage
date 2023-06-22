package mage.cards.o;

import java.util.UUID;

import mage.abilities.common.SagaAbility;
import mage.abilities.effects.Effects;
import mage.abilities.effects.common.BecomesMonarchSourceEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.constants.SagaChapter;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.permanent.token.HumanKnightToken;
import mage.game.permanent.token.HumanSoldierToken;
import mage.target.TargetPermanent;

/**
 *
 * @author Susucr
 */
public final class OathOfEorl extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent(SubType.HUMAN, "Human");

    public OathOfEorl(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{R}{W}");
        
        this.subtype.add(SubType.SAGA);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after III.)
        SagaAbility sagaAbility = new SagaAbility(this);


        // I-- Create two 1/1 white Human Soldier creature tokens.
        sagaAbility.addChapterEffect(
            this, SagaChapter.CHAPTER_I, new CreateTokenEffect(new HumanSoldierToken(), 2)
        );

        // II-- Create two 2/2 red Human Knight creature tokens with trample and haste.
        sagaAbility.addChapterEffect(
            this, SagaChapter.CHAPTER_II, new CreateTokenEffect(new HumanKnightToken(), 2)
        );

        Effects chap3Effects = new Effects(
            new AddCountersTargetEffect(CounterType.INDESTRUCTIBLE.createInstance()),
            new BecomesMonarchSourceEffect()
        );

        // III-- Put an indestructible counter on up to one target Human.
        sagaAbility.addChapterEffect(
            this, SagaChapter.CHAPTER_III,
            chap3Effects,
            new TargetPermanent(0, 1, filter)
        );

        this.addAbility(sagaAbility);
    }

    private OathOfEorl(final OathOfEorl card) {
        super(card);
    }

    @Override
    public OathOfEorl copy() {
        return new OathOfEorl(this);
    }
}
