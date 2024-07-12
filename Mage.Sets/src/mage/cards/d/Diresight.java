package mage.cards.d;

import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Diresight extends CardImpl {

    public Diresight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}");

        // Surveil 2, then draw two cards. You lose 2 life.
        this.getSpellAbility().addEffect(new SurveilEffect(2, false));
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy(", then"));
        this.getSpellAbility().addEffect(new LoseLifeSourceControllerEffect(2));
    }

    private Diresight(final Diresight card) {
        super(card);
    }

    @Override
    public Diresight copy() {
        return new Diresight(this);
    }
}
