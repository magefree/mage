package mage.cards.y;

import java.util.UUID;

import mage.abilities.common.SagaAbility;
import mage.abilities.effects.Effects;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.effects.common.DrawCardAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SagaChapter;
import mage.constants.SubType;

/**
 *
 * @author muz
 */
public final class YearOfHell extends CardImpl {

    public YearOfHell(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{U}{R}");

        this.subtype.add(SubType.SAGA);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after III.)
        SagaAbility sagaAbility = new SagaAbility(this);

        // I, II, III -- Each player draws a card. This Saga deals 2 damage to each player.
        sagaAbility.addChapterEffect(
            this, SagaChapter.CHAPTER_I, SagaChapter.CHAPTER_III,
            new Effects(new DrawCardAllEffect(1), new DamagePlayersEffect(2))
        );

        this.addAbility(sagaAbility);
    }

    private YearOfHell(final YearOfHell card) {
        super(card);
    }

    @Override
    public YearOfHell copy() {
        return new YearOfHell(this);
    }
}
