package mage.cards.t;

import mage.Mana;
import mage.abilities.common.SagaAbility;
import mage.abilities.effects.Effects;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.abilities.effects.common.ShuffleIntoLibraryTargetEffect;
import mage.abilities.effects.mana.BasicManaEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SagaChapter;
import mage.constants.SubType;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheBathSong extends CardImpl {

    public TheBathSong(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{U}");

        this.subtype.add(SubType.SAGA);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after III.)
        SagaAbility sagaAbility = new SagaAbility(this);

        // I, II -- Draw two cards, then discard a card.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_I, SagaChapter.CHAPTER_II,
                new DrawDiscardControllerEffect(2, 1)
        );

        // III -- Shuffle any number of target cards from your graveyard into your library. Add {U}{U}
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_III, SagaChapter.CHAPTER_III, new Effects(
                        new ShuffleIntoLibraryTargetEffect(), new BasicManaEffect(Mana.BlueMana(2))
                ), new TargetCardInYourGraveyard(0, Integer.MAX_VALUE)
        );

        this.addAbility(sagaAbility);
    }

    private TheBathSong(final TheBathSong card) {
        super(card);
    }

    @Override
    public TheBathSong copy() {
        return new TheBathSong(this);
    }
}
