package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.SagaAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SagaChapter;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.ChocoboToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SummonFatChocobo extends CardImpl {

    public SummonFatChocobo(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{4}{G}");

        this.subtype.add(SubType.SAGA);
        this.subtype.add(SubType.BIRD);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after IV.)
        SagaAbility sagaAbility = new SagaAbility(this, SagaChapter.CHAPTER_IV);

        // I -- Wark -- Create a 2/2 green Bird creature token with "Whenever a land you control enters, this token gets +1/+0 until end of turn."
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_I, ability -> {
            ability.addEffect(new CreateTokenEffect(new ChocoboToken()));
            ability.withFlavorWord("Wark");
        });

        // II, III, IV -- Kerplunk -- Creatures you control gain trample until end of turn.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_II, SagaChapter.CHAPTER_IV, ability -> {
            ability.addEffect(new GainAbilityControlledEffect(
                    TrampleAbility.getInstance(), Duration.EndOfTurn,
                    StaticFilters.FILTER_PERMANENT_CREATURES
            ));
            ability.withFlavorWord("Kerplunk");
        });
        this.addAbility(sagaAbility);
    }

    private SummonFatChocobo(final SummonFatChocobo card) {
        super(card);
    }

    @Override
    public SummonFatChocobo copy() {
        return new SummonFatChocobo(this);
    }
}
