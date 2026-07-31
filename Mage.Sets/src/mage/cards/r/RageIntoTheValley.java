package mage.cards.r;

import java.util.UUID;

import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.effects.keyword.AmassEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author muz
 */
public final class RageIntoTheValley extends CardImpl {

    public RageIntoTheValley(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}");

        // You draw a card and lose 1 life.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1, true));
        this.getSpellAbility().addEffect(new LoseLifeSourceControllerEffect(1, false).concatBy("and"));

        // Amass Goblins 2.
        this.getSpellAbility().addEffect(new AmassEffect(2, SubType.GOBLIN).concatBy("<br>"));
    }

    private RageIntoTheValley(final RageIntoTheValley card) {
        super(card);
    }

    @Override
    public RageIntoTheValley copy() {
        return new RageIntoTheValley(this);
    }
}
