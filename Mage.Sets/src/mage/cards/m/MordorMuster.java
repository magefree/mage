package mage.cards.m;

import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.effects.keyword.AmassEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MordorMuster extends CardImpl {

    public MordorMuster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{B}");

        // You draw a card and you lose 1 life.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1, "you"));
        this.getSpellAbility().addEffect(new LoseLifeSourceControllerEffect(1).concatBy("and"));

        // Amass Orcs 1.
        this.getSpellAbility().addEffect(new AmassEffect(1, SubType.ORC).concatBy("<br>"));
    }

    private MordorMuster(final MordorMuster card) {
        super(card);
    }

    @Override
    public MordorMuster copy() {
        return new MordorMuster(this);
    }
}
