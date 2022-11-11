package mage.cards.t;

import mage.abilities.common.SagaAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.effects.common.discard.DiscardAndDrawThatManyEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SagaChapter;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.StaticFilters;
import mage.game.permanent.token.DragonToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheElderDragonWar extends CardImpl {

    public TheElderDragonWar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}{R}");

        this.subtype.add(SubType.SAGA);

        // Read ahead
        SagaAbility sagaAbility = new SagaAbility(this, SagaChapter.CHAPTER_III, true);

        // I -- The Elder Dragon War deals 2 damage to each creature and each opponent.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_I,
                new DamageAllEffect(2, StaticFilters.FILTER_PERMANENT_CREATURE),
                new DamagePlayersEffect(2, TargetController.OPPONENT).setText("and each opponent")
        );

        // II -- Discard any number of cards, then draw that many cards.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_II,
                new DiscardAndDrawThatManyEffect(Integer.MAX_VALUE)
        );

        // III -- Create a 4/4 red Dragon creature token with flying.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_III,
                new CreateTokenEffect(new DragonToken())
        );
        this.addAbility(sagaAbility);
    }

    private TheElderDragonWar(final TheElderDragonWar card) {
        super(card);
    }

    @Override
    public TheElderDragonWar copy() {
        return new TheElderDragonWar(this);
    }
}
