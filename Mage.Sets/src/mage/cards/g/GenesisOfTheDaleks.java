package mage.cards.g;

import java.util.UUID;

import mage.abilities.common.SagaAbility;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.constants.SagaChapter;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.game.permanent.token.DalekToken;

/**
 *
 * @author anonymous
 */
public final class GenesisOfTheDaleks extends CardImpl {

    public GenesisOfTheDaleks(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{B}{B}");
        
        this.subtype.add(SubType.SAGA);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after IV.)
        SagaAbility sagaAbility = new SagaAbility(this, SagaChapter.CHAPTER_III);
        // I, II, III -- Create a 3/3 black Dalek artifact creature token with menace for each lore counter on Genesis of the Daleks.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_I, SagaChapter.CHAPTER_III,
                new CreateTokenEffect(new DalekToken(), new CountersSourceCount(CounterType.LORE)));
        // IV -- Target opponent faces a villainous choice -- Destroy all Dalek creatures and each of your opponents loses life equal to the total power of Daleks that died this turn, or destroy all non-Dalek creatures.

        this.addAbility(sagaAbility);
    }

    private GenesisOfTheDaleks(final GenesisOfTheDaleks card) {
        super(card);
    }

    @Override
    public GenesisOfTheDaleks copy() {
        return new GenesisOfTheDaleks(this);
    }
}
