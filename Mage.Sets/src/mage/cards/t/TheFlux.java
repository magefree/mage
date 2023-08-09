package mage.cards.t;

import mage.Mana;
import mage.abilities.common.SagaAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.ExileTopXMayPlayUntilEndOfTurnEffect;
import mage.abilities.effects.mana.BasicManaEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SagaChapter;
import mage.constants.SubType;
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheFlux extends CardImpl {

    public TheFlux(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}{R}");

        this.subtype.add(SubType.SAGA);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after VI.)
        SagaAbility sagaAbility = new SagaAbility(this, SagaChapter.CHAPTER_VI);

        // I -- The Flux deals 4 damage to target creature an opponent controls.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_I, SagaChapter.CHAPTER_I,
                new DamageTargetEffect(4), new TargetOpponentsCreaturePermanent()
        );

        // II, III, IV, V -- Exile the top card of your library. You may play that card this turn.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_II, SagaChapter.CHAPTER_V,
                new ExileTopXMayPlayUntilEndOfTurnEffect(
                        1, false, Duration.EndOfTurn
                )
        );

        // VI -- Add six {R}.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_VI,
                new BasicManaEffect(Mana.RedMana(6))
                        .setText("Add six {R}")
        );
        this.addAbility(sagaAbility);
    }

    private TheFlux(final TheFlux card) {
        super(card);
    }

    @Override
    public TheFlux copy() {
        return new TheFlux(this);
    }
}
