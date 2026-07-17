package mage.cards.t;

import mage.abilities.common.SagaAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SagaChapter;
import mage.constants.SubType;
import mage.game.permanent.token.GalactusToken;
import mage.target.common.TargetNonlandPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheComingOfGalactus extends CardImpl {

    public TheComingOfGalactus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}{B}{G}");

        this.subtype.add(SubType.SAGA);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after IV.)
        SagaAbility sagaAbility = new SagaAbility(this, SagaChapter.CHAPTER_IV);

        // I -- Destroy up to one target nonland permanent.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_I, new DestroyTargetEffect(), new TargetNonlandPermanent(0, 1));

        // II, III -- Each opponent loses 2 life.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_II, SagaChapter.CHAPTER_III, new LoseLifeOpponentsEffect(2));

        // IV -- Create Galactus, a legendary 16/16 black Elder Alien creature token with flying, trample, and "Whenever Galactus attacks, destroy target land."
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_IV, new CreateTokenEffect(new GalactusToken()));
        this.addAbility(sagaAbility);
    }

    private TheComingOfGalactus(final TheComingOfGalactus card) {
        super(card);
    }

    @Override
    public TheComingOfGalactus copy() {
        return new TheComingOfGalactus(this);
    }
}
