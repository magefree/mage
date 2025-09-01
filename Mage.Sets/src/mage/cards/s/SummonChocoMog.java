package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.SagaAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SagaChapter;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SummonChocoMog extends CardImpl {

    public SummonChocoMog(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.SAGA);
        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.MOOGLE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after IV.)
        SagaAbility sagaAbility = new SagaAbility(this, SagaChapter.CHAPTER_IV);

        // I, II, III, IV -- Stampede! -- Other creatures you control get +1/+0 until end of turn.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_I, SagaChapter.CHAPTER_IV, ability -> {
            ability.addEffect(new BoostControlledEffect(1, 0, Duration.EndOfTurn, true));
            ability.withFlavorWord("Stampede!");
        });
        this.addAbility(sagaAbility);
    }

    private SummonChocoMog(final SummonChocoMog card) {
        super(card);
    }

    @Override
    public SummonChocoMog copy() {
        return new SummonChocoMog(this);
    }
}
